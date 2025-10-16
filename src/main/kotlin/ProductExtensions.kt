// 경로: src/main/kotlin/ProductExtensions.kt

// 날짜(LocalDate)와 날짜 단위(ChronoUnit) 계산 도구를 사용하기 위해 import 하기
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * 'Product' 클래스에 유통기han까지 남은 일수를 계산하는 기능을 추가(확장)
 * @return Long? 타입은 남은 일수(Long)를 반환하거나, 유통기한이 없으면 null을 반환한다는 의미
 */
fun Product.getDaysUntilExpiration(): Long?
{
    // this.expirationDate는 이 함수를 호출한 Product 객체의 유통기한을 가리킵니다.
    // '?.let'은 expirationDate가 null이 아닐 경우에만 중괄호 안의 코드를 실행시키는 안전장치
    return this.expirationDate?.let { expirationDateValue ->
        // ChronoUnit.DAYS.between() 함수는 두 날짜 사이의 일수를 계산
        // LocalDate.now()는 현재 날짜를 가져옵니다.
        ChronoUnit.DAYS.between(LocalDate.now(), expirationDateValue)
    }
}

/**
 * 'Product' 클래스에 상품의 할인율을 계산하는 기능을 확장
 * @param expiryWarningDays 할인을 시작할 기준일 (예: 3일)
 * @param discountPolicy 날짜별 할인율 규칙이 담긴 맵
 * @return Double 타입의 할인율을 반환 (예: 0.3 for 30%)
 */
fun Product.getDiscountRate(expiryWarningDays: Long, discountPolicy: Map<Long, Double>): Double
{
    // 위에서 만든 확장 함수를 호출하여 유통기한까지 남은 일수를 가져옵니다.
    val daysLeft = getDaysUntilExpiration()

    // 유통기한이 존재하고(null이 아니고), 남은 일수가 할인 기준일보다 적을 경우에만 할인 로직을 실행합니다.
    if (daysLeft != null && daysLeft < expiryWarningDays) {
        // 'when'은 여러 조건 중 하나를 선택하여 실행하는 강력한 조건문입니다.
        return when {
            daysLeft <= 0 -> discountPolicy[0L] ?: 0.0 // 남은 일수가 0일 이하일 때
            daysLeft == 1L -> discountPolicy[1L] ?: 0.0 // 남은 일수가 정확히 1일일 때
            daysLeft == 2L -> discountPolicy[2L] ?: 0.0 // 남은 일수가 정확히 2일일 때
            else -> 0.0 // 그 외의 경우 (예: 3일)는 할인 없음
            // '?: 0.0'은 Elvis 연산자로, 만약 discountPolicy에서 값을 찾지 못하면(null이면) 0.0을 기본값으로 사용
        }
    }
    // 할인 조건에 해당하지 않으면 0.0 (할인 없음)을 반환
    return 0.0
}

/**
 * 'Product' 클래스에 할인율이 적용된 최종 가격을 계산하는 기능을 확장
 * @return Int 타입의 최종 할인 가격을 반환
 */
fun Product.getDiscountedPrice(expiryWarningDays: Long, discountPolicy: Map<Long, Double>): Int {
    // 위에서 만든 getDiscountRate 함수를 호출하여 현재 상품의 할인율을 가져오기
    val discountRate = getDiscountRate(expiryWarningDays, discountPolicy)
    // 최종 가격 계산: 원가 * (1 - 할인율).
    // toInt()를 사용하여 결과를 정수(Int)로 변환
    return (this.price * (1 - discountRate)).toInt()
}