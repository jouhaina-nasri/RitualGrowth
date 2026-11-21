package com.yoterra.hippo.helpers;

import java.io.Closeable;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class AppPasswordsManager implements Closeable{
	
	private static final String VALIDITY_CHECK_HEADER = "-vc-";
	
	public static class PassDef {
		private final String name, description;
		private final boolean required;
		
		public static PassDef make (String name, boolean required) {
			return make(name, name, required);
		}
		
		public static PassDef make(String name, String description) {
			return make(name, description, true);
		}
		
		public static PassDef make(String name) {
			return make(name, name, true);
		}
		
		public static PassDef make(String name, String description, boolean required) {
			return new PassDef(name, description, required);
		}

		private PassDef(String name, String description, boolean required) {
			super();
			validateName(name);
			this.name = name;
			this.description = description;
			this.required = required;
		}
		
		
		private void validateName(String name) {
			if(StringUtils.containsWhitespace(name))
				throw new IllegalArgumentException("Password names can not contain white spaces.");
		}

		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public boolean isRequired() {
			return required;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PassDef other = (PassDef) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
	

	private String encryptedPasswordsFileLocation;
	
	private Map<String, PassDef> passwordDefinitions;
	
	private Scanner scanner;
	
	public static String getJarFileLocation() {
		try {
			String jarPath = AppPasswordsManager.class.getProtectionDomain()
			          .getCodeSource().getLocation().toURI().getPath();
			Validate.notNull(jarPath, "Could not determine the jar file location.");
			return jarPath;
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static Map<String,String> getPasswordsFromFile(String filePath, Collection<PassDef> passwordDefs) {
		try(AppPasswordsManager apm = new AppPasswordsManager(filePath, passwordDefs)){
			return apm.getPasswords();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static Map<String,String> getPasswordsFromFile(String passesFileName, PassDef ... passwordDefs) {
		return getPasswordsFromFile(passesFileName, Arrays.asList(passwordDefs));
	}
	
	public static Map<String,String> getPasswordsFromFile(String passesFileName, String ... passwordNames) {
		return getPasswordsFromFile(passesFileName, Arrays.stream(passwordNames).map(PassDef::make).collect(Collectors.toList()));
	}
	
	public AppPasswordsManager(String encryptedPasswordsFileLocation, Collection<PassDef> passwordDefs) {
		Validate.notBlank(encryptedPasswordsFileLocation, "Encrypted passwords file location is required.");
		Validate.notEmpty(passwordDefs, "Password definitions are required.");
		this.encryptedPasswordsFileLocation = encryptedPasswordsFileLocation;
		this.passwordDefinitions = passwordDefs.stream().collect(Collectors.toMap(PassDef::getName, p->p));
		
		this.scanner = new Scanner(System.in);
	}
	
	public AppPasswordsManager(String encryptedPasswordsFileLocation, PassDef ... passwordDefs) {
		this(encryptedPasswordsFileLocation, Arrays.asList(passwordDefs));
	}
	
	public AppPasswordsManager(String encryptedPasswordsFileLocation, String ... passwordNames) {
		this(encryptedPasswordsFileLocation, Arrays.stream(passwordNames).map(PassDef::make).collect(Collectors.toList()));
	}
	
	public Map<String, String> getPasswords(){
		
		Map<String, String> pmap = new TreeMap<String, String>();
		File f = new File(encryptedPasswordsFileLocation);
		if(!f.exists()) {
			System.out.println("The specified encrypted passwords file does not exist. Creating a new one ...");
			obtainMissingPasswords(pmap, passwordDefinitions);
			String masterPass = obtainMasterPassword();
			byte [] encBytes = encryptPasswords(pmap, masterPass);
			writePasswordsFile(f, encBytes);
			System.out.println("Encrypted passwords stored in: " + f.getAbsolutePath());
		}else {
			if (!f.canRead() || !f.isFile()) 
				throw new IllegalArgumentException("The specified file is not readable file");
			
			System.out.println("The specified encrypted passwords file exists.");

			byte [] encPasses = readPasswordsFile(f);
			String masterPass = obtainMasterPassword();
			decryptPasswords(pmap, encPasses, masterPass);
			boolean updated = obtainMissingPasswords(pmap, passwordDefinitions);
			if(updated) {
				byte [] encryptPasswords = encryptPasswords(pmap, masterPass);
				writePasswordsFile(f, encryptPasswords);
				System.out.println("Encrypted passwords updated and stored in: " + f.getAbsolutePath());
			}
		}
		return pmap;
	}

	private boolean obtainMissingPasswords(Map<String, String> pmap, Map<String, PassDef> passwordDefinitions) {
		if(pmap.keySet().containsAll(passwordDefinitions.keySet()))
			return false;
		
		System.out.println("Please provide the following application passwords: ");
		for (PassDef pdef : passwordDefinitions.values()) {
			if(!pmap.containsKey(pdef.getName())) {
				System.out.print(pdef.getDescription() + " [required = " + pdef.isRequired() + "]: ");
				String pass = readPassword();
				pmap.put(pdef.getName(), pass);
			}
		}
		return true;
	}
	
	private String obtainMasterPassword() {
		System.out.print("The master password is used to encrypt/decrypt the application passwords. \nPlease provide the 'master' password: ");
		String mpass = readPassword();
		return mpass;
	}
	
	private byte [] encryptPasswords(Map<String, String> pmap, String masterPass) {
		StringBuilder sb = new StringBuilder(VALIDITY_CHECK_HEADER).append("\n");
		for (Entry<String, String> e: pmap.entrySet()) {
			sb.append(e.getKey()).append(" ").append(e.getValue()).append("\n");
		}
		byte [] encBytes = HippoCrypter.encryptText(sb.toString(), masterPass);
		return encBytes;
	}
	
	@SuppressWarnings("unused")
	private Map<String, String> decryptPasswords(byte [] encBytes, String masterPass) {
		Map<String, String> pmap = new TreeMap<String, String>();
		decryptPasswords(pmap, encBytes, masterPass);
		return pmap;
	}
	
	private void decryptPasswords(Map<String, String> pmap, byte [] encBytes, String masterPass) {
		String decryptedString = HippoCrypter.decryptToText(encBytes, masterPass);
		String[] pairs = StringUtils.split(decryptedString, "\n");
		Validate.isTrue(pairs.length > 1 && VALIDITY_CHECK_HEADER.equals(pairs[0]), "Invalid encrypted passwords format");
		for (int i = 1; i < pairs.length; i++) {
			String[] parr = StringUtils.split(pairs[i], " ");
			Validate.isTrue(parr.length == 2, "Invalid encrypted passwords format");
			pmap.put(parr[0], parr[1]);
		}
	}
	
	private String readPassword() {
		Console console = System.console();
		String pass = null;
		if(console != null) {
			char[] passarr = console.readPassword();
			pass = new String(passarr);
		}else {
			pass = scanner.nextLine();
		}
		validatePassword(pass);
		return pass;
	}
	
	private void validatePassword(String pass){
		if(StringUtils.containsWhitespace(pass)) 
			throw new IllegalArgumentException("Passwords can not contain white spaces.");
	}
	
	private void writePasswordsFile(File f, byte [] encPasswords) {
		try {
			FileUtils.writeByteArrayToFile(f, encPasswords);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private byte [] readPasswordsFile(File f) {
		try {
			return FileUtils.readFileToByteArray(f);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public void close() throws IOException {
		if(scanner != null) {
			scanner.close();
		}
	}
}
