// 경로: src/main/kotlin/Main.kt

// 'LocalDate'라는 날짜 관련 기능을 사용하기 위해 필요한 import 구문
import java.time.LocalDate

// 메인 함수 지정하기, 전반적인 규칙을 정의
fun main()
{
    // 재고 부족 알림을 보낼 기준을 30%로 설정 (0.3 = 30%)
    val stockThresholdRate = 0.3
    // 유통기한 할인을 시작할 기준을 3일로 설정
    val expiryWarningDays = 3L   // 'L'은 이 숫자가 Long 타입임을 명시합니다. 날짜 계산 시 주로 사용됩니다.

    // 'mapOf' 함수를 사용해 유통기한까지 남은 날짜(Key)와 그에 따른 할인율(Value)을 정의
    // 이렇게 하면 특정 날짜의 할인율을 쉽고 빠르게 찾아올 수 있습니다.
    val discountPolicy = mapOf(
        3L to 0.0, // 3일 남음: 할인 없음 (0%)
        2L to 0.3, // 2일 남음: 30% 할인
        1L to 0.5, // 1일 남음: 50% 할인
        0L to 0.7  // 당일(0일 이하): 70% 할인
    )

    // 시스템을 실행하는 데 필요한 초기 상품 데이터와 판매 데이터를 만들기
    // 'listOf' 함수를 사용하여 편의점의 모든 상품 정보를 리스트 형태로 생성
    // 각 상품은 Product 클래스를 통해 이름, 가격 등의 정보를 담은 객체로 만들어집니다.
    val initialProducts = listOf(
        // Product(이름, 가격, 카테고리, 현재재고, 유통기한, 초기재고)
        // LocalDate.now()는 '오늘 날짜'를, .plusDays(n)는 'n일 뒤'를 의미
        Product("새우깡", 1500, ProductCategory.SNACK, 20, null, 30),
        Product("콜라 500ml", 1500, ProductCategory.BEVERAGE, 20, null, 20),
        Product("김치찌개 도시락", 5500, ProductCategory.FOOD, 20, LocalDate.now().plusDays(2), 20),
        Product("참치마요 삼각김밥", 1500, ProductCategory.FOOD, 22, LocalDate.now().plusDays(1), 22),
        Product("딸기 샌드위치", 2800, ProductCategory.FOOD, 5, LocalDate.now(), 10),
        Product("물 500ml", 1000, ProductCategory.BEVERAGE, 32, null, 32),
        Product("초코파이", 3000, ProductCategory.SNACK, 23, null, 23),
        Product("즉석라면", 1200, ProductCategory.FOOD, 45, null, 45)
    )

    // 'mapOf' 함수를 사용하여 오늘 판매된 상품의 이름(Key)과 판매 수량(Value)을 기록
    val todaySales = mapOf(
        "새우깡" to 15,
        "콜라 500ml" to 12,
        "참치마요 삼각김밥" to 10,
        "초코파이" to 8,
        "물 500ml" to 7,
        "딸기 샌드위치" to 3,
        "김치찌개 도시락" to 17
    )

    // 준비된 데이터와 설정을 바탕으로 재고 관리 시스템을 실행

    // 1. InventoryManager(재고 관리자) 객체를 생성하고, 초기 상품 목록을 전달
    val inventoryManager = InventoryManager(initialProducts)

    // 2. 재고 관리자에게 오늘의 판매 기록을 전달하여 현재 재고를 업데이트하도록 하기
    inventoryManager.processSales(todaySales)

    // 3. 재고 관리자에게 최종 보고서 생성을 요청합니다.
    //    보고서 생성에 필요한 모든 데이터(판매 기록, 설정값 등)를 전달
    inventoryManager.generateReport(todaySales, stockThresholdRate, expiryWarningDays, discountPolicy)
}