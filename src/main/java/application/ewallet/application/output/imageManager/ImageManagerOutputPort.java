package application.ewallet.application.output.imageManager;

import application.ewallet.domain.exceptions.WalletException;

public interface ImageManagerOutputPort {

    String uploadImage(String content) throws WalletException;
}
