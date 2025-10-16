// 경로: src/main/kotlin/AdvancedProduct.kt

/**
 * '판매 가능한' 모든 상품들이 반드시 지켜야 할 '최소한의 약속(규칙)'을 정의하는 인터페이스
 * 인터페이스는 특정 기능을 반드시 구현하도록 강제하여 코드의 일관성과 안정성을 높여줍니다.
 */
interface Sellable {
    // 이 인터페이스를 따르는 클래스는 반드시 'name'이라는 이름의 String 속성을 가져야 합니다.
    val name: String
    // 또한, 반드시 'price'라는 이름의 Int 속성을 가져야 합니다.
    val price: Int
}

/**
 * 'Sellable' 인터페이스의 약속을 실제로 지키는 예시 클래스입니다.
 * '1+1' 상품이나 묶음 할인 상품 같은 프로모션 상품을 표현하기 위해 만들었습니다.
 * @param baseProduct 기본이 되는 원본 상품 정보 (예: 새우깡)
 * @param promotionInfo 프로모션 정보 문자열 (예: "1+1 행사 상품")
 */
// ': Sellable'은 "Sellable 인터페이스의 규칙을 따르겠습니다"라는 의미의 구현 선언
data class PromotionProduct(
    val baseProduct: Product,
    val promotionInfo: String
) : Sellable {
    // 'override'는 인터페이스가 약속한 속성을 여기서 직접 구현하겠다는 의미
    // 기존 상품 이름에 프로모션 정보를 덧붙여 새로운 이름을 만듭니다. (예: "새우깡 (1+1 행사 상품)")
    override val name: String = "${baseProduct.name} ($promotionInfo)"
    // 가격은 기본 상품의 가격을 그대로 사용하기
    override val price: Int = baseProduct.price
}