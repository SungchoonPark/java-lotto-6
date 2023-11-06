package lotto.validator;

import lotto.constant.ErrorMessage;

public class PurchaseAmountValidator extends Validator {

    @Override
    public void validate(String purchaseAmount) {
        validateEmptyInput(purchaseAmount);
        validatePurchaseAmount(purchaseAmount);
    }
    // 금액이 숫자가 아닌 문자가 들어온 경우
    private static void validatePurchaseAmount(String purchaseAmount) throws IllegalArgumentException{
        try {
            Integer.parseInt(purchaseAmount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_NUMBER_ERROR.getMessage());
        }
    }

    // 구매금액이 1000원 이상인지 확인하는 메서드
    public static boolean isOverMinPurchaseAmount(Integer purchaseAmount) {
        if (purchaseAmount >= 1000) {
            return true;
        }
        return false;
    }

    // 금액이 1000원 단위인지 확인하는 메서드 -> ClientInform에서 체크
    public static boolean isThousandWonAmount(Integer purchaseAmount) {
        if (purchaseAmount % 1000 == 0) {
            return true;
        }
        return false;
    }
}
