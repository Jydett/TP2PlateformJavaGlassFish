package fr.polytech.constants;

public interface Constants {

    interface Errors {
        String AUTHENTICATION_FAILED = "Authentification échouée";
        String EMAIL_ALREADY_USED = "Ce mail est déjà utilisé";
        String MUST_BE_CONNECTED_TO_POST = "Vous devez etre connecté pour poster un message";
        String MUST_BE_CONNECTED_TO_REACT = "Vous devez etre connecté pour donner une réaction à un message";
        String LOGIN_ALREADY_USED = "Cet login est déjà utilisé";
        String MESSAGE_DOESNT_EXIST = "Impossible de trouver ce message";
        String LOGIN_IS_EMPTY = "Le login ne peut pas être vide";
        String PASSWORD_IS_EMPTY = "Le mot de passe ne peut pas être vide";
        String USERNAME_IS_EMPTY = "Le nom de compte ne peut pas être vide";
        String MAIL_NOT_VALID = "Ce mail n'est pas valide";
        String COMMENT_IS_EMPTY = "Votre commentaire ne peux pas être vide";
        String NEGATIVE_PAGE_NUMBER = "Le numéro de page doit être supérieur 0";
    }

    interface Routing {
        String HOME_PAGE = "home.xhtml";
    }
}
