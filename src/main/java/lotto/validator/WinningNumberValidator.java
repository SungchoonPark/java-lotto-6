package lotto.validator;

import lotto.constant.ErrorMessage;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WinningNumberValidator extends Validator {
    private static final Pattern VALID_LOTTO_NUMBER_REGEXP = Pattern.compile("[0-9]+");
    private static final String NUMBER_SEPARATOR = ",";

    @Override
    public void validate(String winningNumber) throws IllegalArgumentException {
        validateEmptyInput(winningNumber);
        validateSeparator(winningNumber);
        validateInvalidWinningNumbers(winningNumber);
        validateWinningNumber(parsingWinningNumber(winningNumber));
    }

    private void validateSeparator(String winningNumber) throws IllegalArgumentException {
        if (!winningNumber.contains(NUMBER_SEPARATOR)) {
            throw new IllegalArgumentException(ErrorMessage.SEPARATOR_ERROR.getMessage());
        }
    }

    private void validateInvalidWinningNumbers(String winningNumber) throws IllegalArgumentException {
        String[] winningLottoNumbers = winningNumber.split(NUMBER_SEPARATOR);
        for (String winningNumberStr : winningLottoNumbers) {
            if (!isValidWinningNumber(winningNumberStr)) {
                throw new IllegalArgumentException(ErrorMessage.NOT_NUMBER_ERROR.getMessage());
            }
        }
    }

    private boolean isValidWinningNumber(String lottoNumber) throws IllegalArgumentException {
        Matcher matcher = VALID_LOTTO_NUMBER_REGEXP.matcher(lottoNumber);
        return matcher.matches();
    }

    private void validateWinningNumber(List<Integer> winningNumbers) throws IllegalArgumentException {
        validateWinningLottoNumber(winningNumbers);
        validateWinningNumberBound(winningNumbers);
        validateDupleWinningNumber(winningNumbers);
    }

    private List<Integer> parsingWinningNumber(String winningLottoNumber) {
        return Arrays.stream(winningLottoNumber.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    private void validateWinningNumberBound(List<Integer> winningNumbers) throws IllegalArgumentException {
        if(winningNumbers.stream().anyMatch(number -> !isValidNumberBound(number))){
            throw new IllegalArgumentException(ErrorMessage.NUMBER_BOUND_ERROR.getMessage());
        }
    }

    private boolean isValidNumberBound(int winningNumber) {
        if (winningNumber < 1 || winningNumber > 45) {
            return false;
        }
        return true;
    }

    private void validateWinningLottoNumber(List<Integer> winningNumbers) throws IllegalArgumentException {
        if(winningNumbers.size() != 6) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_NUM_ERROR.getMessage());
        }
    }

    private void validateDupleWinningNumber(List<Integer> winningNumbers) throws IllegalArgumentException {
        if (countNotDupleLottoNumber(winningNumbers) != 6) {
            throw new IllegalArgumentException(ErrorMessage.DUPLE_NUM_ERROR.getMessage());
        }
    }

    private int countNotDupleLottoNumber(List<Integer> winningNumbers) {
        return (int) winningNumbers.stream().distinct().count();
    }
}
