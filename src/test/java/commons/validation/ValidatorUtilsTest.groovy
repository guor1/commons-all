package commons.validation


import commons.model.DateRange
import spock.lang.Specification
import spock.lang.Unroll

import java.text.SimpleDateFormat

class ValidatorUtilsTest extends Specification {

    @Unroll("#specName")
    def "testValidateEntity"() {
        when: "调用校验用户方法"
        var promotion = new Promotion(id: id, promotionName: promotionName, promotionDate: promotionDate)
        var response = ValidatorUtils.validateEntity(false, promotion)

        then:
        with(response) {
            response.isEmpty() == res
        }

        where:
        id   | promotionName | promotionDate | res   | specName
        null | "test1"       | date()        | false | "活动ID不为空"
        1    | null          | date()        | false | "活动名称不为空"
        1    | "test1"       | null          | false | "活动时间不为空"
        1    | "test"        | date()        | false | "活动名称长度为[5,20]"
        1    | "test2"       | date()        | true  | "校验成功"
    }

    private static DateRange date() {
        var sdf = new SimpleDateFormat("yyyy/MM/dd")
        var d1 = sdf.parse("2022/08/01")
        var d2 = sdf.parse("2022/08/10")
        return new DateRange(d1, d2)
    }
}
