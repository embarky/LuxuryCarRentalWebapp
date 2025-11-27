package ch.unil.softarch.luxurycarrental.ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.UUID;

import ch.unil.softarch.luxurycarrental.client.AdminClient;
import ch.unil.softarch.luxurycarrental.client.CustomerClient;

@Named
@SessionScoped
public class ResetPasswordBean implements Serializable {
}