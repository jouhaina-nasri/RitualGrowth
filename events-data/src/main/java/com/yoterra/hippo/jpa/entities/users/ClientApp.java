package com.yoterra.hippo.jpa.entities.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import com.yoterra.hippo.jpa.entities.ActiveEntity;


/**
 * Associates messaging tokens to a user. The tokens are used for sending push notifications!
 * Typically, each token is associated with one device. If the user has the app installed on multiple devices, 
 * there should be multiple tokens associated with one user. Some tokens could be deprecated!  
 * @author mihajlo
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "CLIENT_APP")
public class ClientApp extends ActiveEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(updatable = false)
	private String messagingToken;
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessagingToken() {
		return messagingToken;
	}

	public void setMessagingToken(String messagingToken) {
		this.messagingToken = messagingToken;
	}
}
