// 경로: src/main/kotlin/Product.kt

// 'LocalDate'라는 날짜 관련 도구를 사용하기 위해 가져오기.
import java.time.LocalDate

/**
 * 상품의 카테고리(종류)를 체계적으로 분류하기 위한 열거형 클래스입니다.
 * 'enum'을 사용하면 정해진 값(FOOD, BEVERAGE, SNACK) 외에는 사용할 수 없도록 강제하여 실수를 방지합니다.
 * @param koreanName 보고서 출력 시 사용할 한글 카테고리 이름을 저장합니다.
 */
enum class ProductCategory(val koreanName: String)
{
    FOOD("식품류"),       // 식품 카테고리
    BEVERAGE("음료류"),   // 음료 카테고리
    SNACK("과자류")       // 과자 카테고리
}

/*
 * 'data class'는 데이터를 다루는 데 최적화된 클래스로 사용했습니다.
 * @property name 상품의 이름 (변경 불가능)
 * @property price 상품의 가격 (변경 불가능)
 * @property category 상품의 카테고리 (FOOD, BEVERAGE, SNACK 중 하나)
 * @property stock 현재 남은 재고 수량 (판매에 따라 계속 변경 가능)
 * @property expirationDate 상품의 유통기한. 'LocalDate?' 타입은 날짜가 없을 수도(null) 있음을 의미
 * @property initialStock 하루가 시작될 때의 초기 재고량. 재고 부족 판단 및 발주량 계산의 기준
 */
data class Product
    (
    // val: 불변
    val name: String,
    val price: Int,
    val category: ProductCategory,

    // var: 가변
    var stock: Int,

    // '?' 기호: 이 값은 날짜 정보가 있거나, 없을 수도(null) 있다는 의미. 파이썬에서 배움
    val expirationDate: LocalDate? = null,

    // 초기 재고(initialStock)는 따로 값을 주지 않으면, 맨 처음 설정된 재고(stock) 값으로 자동 할당
    val initialStock: Int = stock
)