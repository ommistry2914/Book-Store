/* This controller will handle / call File classes */
package Backend.Controller;

import Backend.FileManagment.FetchBookCoverByFile;

public class PerformOperationOnBookData implements OperationsOnBookData{

    @Override
    public String fetchBookCover() {
        return FetchBookCoverByFile.fetchBookCoverFromDevice();
    }

}
