package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppUtilTest {

    @Test
    public void getImage_existingImage() {
        assertNotNull(AppUtil.getImage("/images/address_book_32.png"));
    }

    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void loadImage_existingImage() {
        assertNotNull(AppUtil.loadImage("/images/address_book_32.png"));
    }

    @Test
    public void loadImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.loadImage(null));
    }

    @Test
    public void loadImage_nonExistingImage_loadsFallbackImage() {
        assertNotNull(AppUtil.loadImage("/images/non_existing_image.png"));
    }

    @Test
    public void loadImage_validClasspathImage_success() {
        assertNotNull(AppUtil.loadImage("/images/placeholder-pet-logo.png"));
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class, errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }
}
