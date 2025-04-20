package edu.ijse.therapycenter.bo;

import edu.ijse.therapycenter.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }

    public enum BOType {
        USER
    }

    public SuperBO getBO(BOType type) {
        return switch (type) {
            case USER -> new UserBOImpl();
        };
    }

}