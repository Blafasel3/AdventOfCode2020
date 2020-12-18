package org.example

import org.junit.Test

class Day16 {
    @Test
    fun `part 1`() {
        val blocksOfInput = puzzleInput.split(Regex("\\s+\\n"))
        val rules = parseRules(blocksOfInput)
        val nearbyTicketValues = parseTicketValues(blocksOfInput.last())
        val invalidValues = nearbyTicketValues.map { it.values }.flatMap { ticketValues ->
            ticketValues.map { intValue ->
                val validationByValue = rules.map { rule ->
                    Pair(rule.isValid(intValue), intValue)
                }
                if (validationByValue.all { !it.first }) {
                    intValue
                } else {
                    -1
                }
            }
        }.filter { it > 0 }
        println(invalidValues.sum())
    }

    @Test
    fun `part 2`() {
        val testInput = """
            class: 0-1 or 4-19
            row: 0-5 or 8-19
            seat: 0-13 or 16-19
            
            your ticket:
            11,12,13
            
            nearby tickets:
            3,9,18
            15,1,5
            5,14,9
            20,11,1
         """.trimIndent()

        val blocksOfInput = puzzleInput.split(Regex("\\s+\\n"))
        val rules = parseRules(blocksOfInput)
        val validTickets = getValidNearbyTickets(blocksOfInput.last(), rules)
        val valuesByIndex = mutableMapOf<Int, MutableList<Int>>()
        validTickets.map { it.values }.forEach {
            for (i in it.indices) {
                val values = valuesByIndex.getOrDefault(defaultValue = mutableListOf(), key = i)
                values.add(it[i])
                valuesByIndex[i] = values
            }
        }
        val validityPerIndexAndValue = valuesByIndex.map { entry ->
            rules.map { rule -> entry.value.all { rule.isValid(it) } }.toMutableList()
        }

        val rulePerIndex = mutableMapOf<Int, Rule>()
        while (rulePerIndex.size < rules.size) {
            val element = validityPerIndexAndValue.find { it.count { value -> value } == 1 }
            val indexOf = validityPerIndexAndValue.indexOf(element)
            val ruleIndex = element!!.indexOf(true)
            val matchingRule = rules[ruleIndex]
            validityPerIndexAndValue.forEach { mutableList ->
                mutableList[ruleIndex] = false // blacklist index of currently found rule
            }
            for (i in validityPerIndexAndValue[indexOf].indices) {
                validityPerIndexAndValue[indexOf][i] = false
            }
            rulePerIndex.putIfAbsent(indexOf, matchingRule)
        }

        val myTicket = parseTicketValues(blocksOfInput[1]).first()
        println(myTicket)
        val indexes = rulePerIndex.filter { it.value.name.startsWith("departure") }.keys
        println(indexes)
        println(myTicket.values.filterIndexed() { index, _ -> indexes.contains(index) }
            .map { it.toBigInteger() }
            .reduce { acc, i ->
                println(acc)
                acc * i
            }
        )
    }

    private fun getValidNearbyTickets(
        nearbyTicketBlock: String,
        rules: List<Rule>
    ): List<Ticket> {
        val nearbyTicketValues = parseTicketValues(nearbyTicketBlock)
        return nearbyTicketValues.map { ticket ->
            val isValidTicket = ticket.values.map { intValue ->
                val validationByValue = rules.map { rule ->
                    Pair(rule.isValid(intValue), intValue)
                }
                validationByValue.any { it.first }
            }.reduce { acc, b -> acc && b }
            Pair(ticket, isValidTicket)
        }
            .filter { it.second }
            .map { it.first }
    }

    private fun parseRules(blocksOfInput: List<String>): List<Rule> = blocksOfInput.first().lines().map {
        val split = it.split(": ")
        val name = split.first()
        val ranges = split.last().split(" or ")
            .map { singleRule ->
                val map = singleRule.split("-").map { number -> number.toInt() }
                map.first()..map.last()
            }
        Rule(name, ranges)
    }

    private fun parseTicketValues(ticketBlock: String): List<Ticket> =
        ticketBlock.lines().drop(1) // drop first line
            .map { line ->
                line.split(",")
                    .map { it.toInt() }
            }.map { Ticket(it) }

    private val puzzleInput = """
        departure location: 32-209 or 234-963
        departure station: 47-64 or 83-967
        departure platform: 37-609 or 628-970
        departure track: 29-546 or 567-971
        departure date: 50-795 or 816-960
        departure time: 49-736 or 750-962
        arrival location: 48-399 or 420-967
        arrival station: 49-353 or 360-967
        arrival platform: 37-275 or 298-969
        arrival track: 40-119 or 127-954
        class: 35-750 or 760-968
        duration: 43-162 or 186-963
        price: 30-889 or 914-949
        route: 39-266 or 274-950
        row: 45-366 or 389-954
        seat: 42-765 or 772-955
        train: 30-494 or 518-957
        type: 48-822 or 835-973
        wagon: 32-330 or 342-951
        zone: 36-455 or 462-973

        your ticket:
        109,137,131,157,191,103,127,53,107,151,61,59,139,83,101,149,89,193,113,97

        nearby tickets:
        141,889,637,661,351,108,544,424,330,479,662,448,152,584,408,736,714,299,61,864
        692,855,843,361,21,265,678,716,347,531,56,792,492,656,727,848,149,596,887,862
        819,139,202,774,606,242,646,653,298,111,881,937,118,600,423,354,780,249,639,301
        938,258,482,697,640,678,946,349,153,198,707,601,925,601,677,307,925,840,870,177
        119,724,442,993,137,394,85,673,609,862,631,92,545,591,104,451,130,603,94,946
        468,932,879,522,256,688,587,747,154,735,476,661,312,602,315,314,263,55,203,733
        265,630,795,703,244,848,818,195,692,728,216,637,765,195,697,489,188,302,189,662
        97,300,193,203,87,835,835,205,850,584,690,218,699,734,433,862,149,657,686,691
        493,657,652,170,252,188,915,424,57,302,858,484,520,676,441,730,481,656,307,263
        425,776,137,931,633,402,197,83,574,161,732,85,445,726,786,208,535,773,390,248
        765,689,63,389,523,352,203,50,161,817,604,799,441,719,116,196,680,363,782,521
        876,882,389,543,575,257,333,481,493,852,134,939,114,576,705,915,645,584,259,710
        693,600,818,420,434,536,884,868,571,569,235,423,250,712,158,98,860,512,157,136
        201,882,688,918,244,147,678,619,194,237,931,914,649,446,783,107,207,260,518,652
        690,469,660,915,726,866,348,147,147,63,22,646,933,141,441,443,327,859,98,654
        246,882,869,239,914,342,682,705,243,263,629,641,607,776,193,50,997,701,314,887
        818,700,363,103,672,327,433,91,242,882,147,413,490,729,914,870,476,360,127,262
        138,791,317,866,694,849,55,845,660,699,349,894,630,390,638,838,850,573,433,859
        398,765,264,195,275,538,429,677,939,483,943,481,248,786,434,200,126,920,53,764
        570,928,684,793,779,782,531,343,656,609,688,624,113,84,304,641,314,464,662,93
        914,872,984,638,792,636,104,668,636,303,604,306,728,254,655,88,429,923,248,659
        478,243,884,54,632,920,430,539,108,144,496,274,396,855,204,307,53,129,774,256
        320,430,316,88,840,330,847,629,788,261,551,835,325,711,639,109,274,152,199,887
        236,250,820,662,795,638,507,784,431,654,161,694,704,641,489,108,154,300,872,881
        678,140,424,204,101,915,244,687,869,817,445,847,492,95,945,873,704,319,417,429
        646,206,438,256,541,629,348,856,687,311,268,541,704,876,152,63,141,312,711,523
        781,274,662,531,777,342,926,128,474,182,206,483,939,196,776,60,311,653,520,669
        193,525,275,923,305,343,888,858,143,706,144,438,484,487,299,463,761,987,477,935
        327,856,52,586,95,646,300,722,363,667,116,531,491,570,653,102,350,125,539,701
        865,821,685,522,102,449,728,527,471,87,884,839,357,765,642,655,432,141,155,299
        394,767,149,394,263,352,784,437,600,582,788,469,579,138,696,150,158,924,319,917
        129,633,605,207,136,318,301,654,629,200,597,4,921,654,581,926,441,55,444,446
        450,469,472,363,692,869,579,204,643,133,866,581,91,161,334,109,696,427,822,936
        299,195,155,235,64,136,869,791,723,701,771,207,133,489,188,84,489,881,856,88
        52,533,185,859,52,187,90,593,876,64,394,57,821,944,773,328,782,653,726,733
        865,129,240,602,84,735,821,220,730,685,667,391,543,728,146,301,153,605,321,138
        127,236,398,194,731,333,319,203,251,91,917,941,708,777,427,50,240,727,629,390
        750,93,128,470,638,60,156,589,469,864,6,421,103,532,189,363,113,791,390,238
        308,677,932,631,261,698,105,106,578,265,809,330,266,63,725,156,697,837,701,239
        670,263,691,16,467,750,151,188,591,697,718,943,312,539,350,887,777,644,872,117
        663,530,838,779,700,258,643,305,90,421,621,918,630,884,347,275,275,876,632,864
        528,300,917,676,439,567,155,207,839,60,53,544,877,140,298,165,491,947,889,869
        329,424,882,351,733,209,131,244,677,8,776,323,698,642,94,129,247,781,659,50
        239,774,873,532,490,427,879,523,275,142,690,668,115,346,488,261,675,272,569,570
        686,14,641,603,696,595,779,209,781,680,162,463,156,353,203,424,470,477,652,718
        660,855,473,924,850,925,668,836,208,320,275,838,647,861,662,822,8,310,315,481
        578,916,489,591,985,691,601,847,207,885,673,840,711,851,363,429,653,790,475,576
        275,317,441,727,636,671,979,84,670,97,932,591,639,240,598,153,323,657,858,317
        688,135,124,99,850,781,917,107,674,672,102,857,63,160,720,113,445,675,838,604
        125,52,127,656,542,353,764,87,632,114,689,659,155,609,699,677,608,787,760,468
        853,936,585,714,935,606,686,418,128,840,777,323,710,542,493,438,145,237,577,674
        589,88,301,665,200,657,662,887,131,160,209,689,594,61,592,449,253,185,479,846
        940,876,324,880,328,654,452,945,102,841,597,855,235,847,769,330,320,540,657,946
        855,764,90,857,485,330,696,202,101,726,713,845,862,209,977,577,719,484,604,257
        699,255,155,637,630,769,299,727,914,607,944,599,679,648,522,587,314,436,360,480
        660,638,493,306,323,351,928,105,464,470,235,795,264,666,915,92,130,990,599,344
        908,50,604,855,434,923,670,483,491,580,938,644,689,917,570,435,439,479,578,816
        840,864,664,681,855,822,567,764,364,778,787,929,768,645,760,820,469,919,606,189
        97,594,923,567,997,436,629,262,140,101,631,312,437,861,108,449,55,529,56,492
        397,55,730,570,93,538,187,179,660,862,580,879,640,947,708,494,101,697,361,857
        588,619,520,99,420,248,430,608,303,342,394,316,462,254,573,202,884,696,442,238
        645,716,266,637,688,905,198,651,788,591,138,641,451,132,366,934,687,151,186,522
        100,703,686,631,449,589,569,875,308,609,764,205,846,124,571,471,204,880,764,209
        588,608,13,85,199,486,946,727,840,134,102,305,712,156,679,713,941,252,260,156
        428,636,882,881,307,703,352,274,342,334,56,118,455,629,605,487,679,918,866,839
        524,327,946,877,637,52,484,439,471,441,863,465,63,112,580,481,872,976,141,819
        303,521,91,121,728,393,463,574,89,916,427,760,519,856,191,787,920,258,104,664
        321,204,243,529,986,197,94,86,135,660,434,851,466,241,688,878,145,86,570,915
        326,187,720,601,314,315,697,727,694,316,836,737,455,658,628,162,609,422,85,795
        87,61,678,86,311,669,836,693,448,855,69,129,869,92,307,95,594,934,944,701
        148,818,203,781,856,304,490,319,462,914,51,240,474,710,171,141,855,248,609,98
        792,696,708,234,597,774,923,55,688,189,109,127,390,96,105,11,884,102,154,304
        672,309,696,794,432,320,63,437,780,242,937,536,545,654,236,659,934,986,844,863
        698,202,531,365,583,583,135,62,650,924,83,127,436,50,444,334,819,700,577,731
        764,871,765,391,135,856,103,446,726,197,245,334,399,790,440,365,390,680,733,141
        204,353,301,865,310,889,534,767,463,777,850,108,434,353,189,258,529,652,329,353
        465,265,480,149,929,608,578,358,485,207,661,118,244,310,99,845,526,188,690,450
        729,262,879,315,344,93,54,151,529,236,716,675,429,594,709,191,331,822,817,89
        879,586,349,701,528,816,85,254,115,588,364,107,819,129,99,120,391,252,308,153
        734,865,866,686,662,267,533,698,856,462,437,63,846,325,762,525,59,392,129,923
        192,71,146,346,429,352,793,541,936,348,481,53,577,921,915,259,583,481,701,544
        683,605,543,398,840,935,579,658,114,303,309,682,420,932,734,211,673,316,817,598
        304,440,541,196,645,494,255,109,84,881,667,545,527,568,941,461,609,476,138,312
        585,147,56,524,856,947,664,529,850,424,358,533,918,205,145,844,600,51,765,784
        258,660,540,865,143,109,706,351,316,578,251,192,661,768,569,944,724,844,90,153
        483,126,431,727,159,536,157,190,567,783,92,395,782,236,589,946,763,432,150,607
        583,254,569,571,926,196,764,275,153,489,450,132,988,152,329,690,541,206,96,838
        109,432,876,51,98,598,217,243,723,927,656,782,323,310,933,724,939,257,363,947
        594,194,732,245,190,347,775,132,778,479,701,64,711,665,940,796,451,195,791,733
        724,92,544,474,454,92,577,596,449,262,887,54,258,709,432,488,914,553,527,199
        90,434,721,643,242,859,122,535,879,521,866,115,848,423,326,523,54,867,63,366
        720,639,151,311,835,112,679,574,521,531,262,316,607,201,708,684,102,97,183,192
        189,208,242,702,327,628,635,204,190,691,736,132,239,248,272,275,442,666,158,721
        325,239,677,569,62,542,243,162,641,127,451,819,909,469,692,632,878,238,935,154
        428,783,390,865,873,301,923,351,312,453,101,243,792,925,720,835,411,144,676,691
        162,234,479,593,865,929,671,855,239,629,452,666,778,52,237,299,641,519,124,207
        428,718,519,195,99,326,657,699,733,259,705,508,873,675,592,839,713,782,242,840
        662,191,487,794,685,320,949,778,1,480,256,540,448,257,493,934,914,889,888,312
        608,335,274,926,873,127,192,537,257,525,535,141,919,628,716,679,646,940,86,861
        652,420,127,786,669,235,665,446,649,846,260,245,320,9,194,649,599,818,98,682
        151,849,789,443,854,684,348,927,593,859,782,88,839,444,845,552,546,636,131,750
        311,161,144,894,919,639,482,639,442,326,452,427,247,275,524,445,484,250,155,633
        366,327,142,494,430,318,90,159,524,658,925,261,349,413,581,139,482,705,736,701
        940,723,589,421,247,747,161,136,389,197,59,422,304,430,396,399,576,857,764,342
        472,390,601,609,96,714,785,432,568,259,469,87,567,654,249,894,580,205,470,428
        483,864,679,542,546,94,86,529,822,439,352,573,718,487,468,412,64,733,944,396
        321,871,886,594,651,391,195,540,887,585,789,93,979,137,781,602,857,437,256,266
        653,677,92,451,259,574,598,327,795,945,757,262,425,693,199,714,642,640,479,691
        876,805,521,397,645,107,792,541,455,343,155,708,683,98,601,585,425,95,528,304
        54,475,941,107,518,446,722,92,633,90,672,469,567,774,725,200,312,192,319,269
        131,708,724,523,889,251,159,784,850,114,839,451,627,644,725,641,847,690,630,816
        190,748,307,649,151,113,119,470,670,426,597,318,445,112,733,716,928,704,527,480
        595,426,54,188,667,524,729,596,722,861,735,673,334,527,697,888,777,153,727,389
        104,318,877,931,420,245,857,122,52,761,107,365,608,709,300,148,209,274,603,317
        439,880,103,317,208,926,635,720,859,345,857,299,520,795,666,113,784,231,939,722
        695,197,538,108,525,860,713,425,840,923,12,597,935,653,161,146,245,156,924,851
        208,662,491,61,643,568,535,188,885,154,366,452,451,677,260,116,437,54,918,0
        110,729,530,97,472,453,261,464,260,136,529,736,476,248,793,484,907,155,705,199
        665,116,602,685,249,940,151,881,139,349,585,348,268,854,477,486,350,918,107,148
        706,161,194,299,93,582,353,305,519,453,653,330,86,336,875,206,691,860,546,694
        859,942,920,732,699,543,820,903,879,366,471,348,716,195,651,729,494,861,522,639
        150,780,206,731,600,841,930,146,709,257,709,928,854,321,488,695,365,85,335,822
        939,851,488,677,265,132,494,235,924,605,675,630,157,146,597,74,863,394,451,672
        105,60,352,417,925,100,471,678,204,186,254,466,641,363,653,252,492,156,528,262
        793,129,675,188,139,595,851,647,599,391,889,261,311,685,308,770,935,303,108,653
        476,664,732,889,671,852,586,878,363,399,533,774,396,568,448,208,318,941,721,766
        763,878,137,317,842,938,315,752,863,702,840,90,422,919,136,776,714,721,788,639
        935,682,299,776,191,488,593,630,55,523,260,842,804,707,783,642,429,96,657,638
        63,449,467,94,590,522,734,545,842,650,533,587,452,153,922,346,769,917,490,486
        682,171,94,348,665,681,310,727,918,712,129,578,531,572,325,438,353,715,648,193
        4,946,883,209,715,92,257,463,448,452,764,725,736,772,472,442,447,61,533,151
        107,268,685,439,105,247,920,192,594,683,876,640,860,762,602,153,486,534,472,817
        774,575,594,445,864,589,567,97,89,126,792,650,365,945,130,773,718,91,471,422
        598,97,668,420,157,735,538,778,690,675,205,633,474,945,893,590,578,119,852,854
        948,592,575,114,436,882,310,399,675,690,430,193,139,273,840,680,792,191,845,472
        135,545,472,145,275,810,821,601,260,652,715,919,848,690,568,421,668,855,390,484
        52,300,448,529,658,601,935,258,918,664,942,439,503,729,535,62,159,722,50,585
        108,704,717,604,883,99,726,727,62,95,587,558,532,541,791,723,200,235,243,702
        147,765,23,683,398,664,760,773,315,523,330,474,838,190,730,717,112,793,666,727
        602,494,606,704,56,310,658,102,862,109,885,100,123,455,692,783,949,837,786,700
        604,785,777,867,466,342,130,544,89,926,460,719,193,153,425,318,426,924,649,301
        583,64,448,244,843,686,572,237,489,134,316,593,679,818,917,424,303,809,704,315
        914,722,351,10,685,636,682,840,247,423,879,468,131,301,59,664,840,488,781,877
        673,856,521,398,58,697,110,762,470,157,424,147,89,581,498,939,322,128,147,942
        197,298,462,109,673,90,845,207,479,321,481,485,606,478,947,680,463,463,253,123
        719,438,143,595,822,762,118,318,94,12,718,438,794,540,644,931,871,668,303,677
        678,898,327,304,583,569,790,722,917,593,817,629,191,305,160,483,480,436,843,204
        103,927,157,921,198,470,264,307,630,527,844,391,863,987,392,87,519,193,776,945
        609,679,994,64,142,571,463,52,156,789,782,472,729,942,275,436,305,533,430,702
        83,428,788,946,642,923,537,348,423,943,712,720,735,391,301,207,778,360,265,912
        921,949,917,704,320,307,738,154,750,634,161,526,346,202,275,479,837,639,442,431
        464,390,780,718,250,860,135,309,601,840,63,176,299,595,699,360,309,868,83,933
        542,538,854,328,97,196,554,193,106,440,685,845,315,150,205,96,144,687,871,187
        366,816,601,312,784,432,678,344,934,663,916,308,312,731,204,499,535,946,885,348
        728,311,139,394,538,673,647,595,723,321,451,202,779,310,485,654,127,337,476,647
        209,424,471,780,111,437,262,942,750,761,148,635,987,104,888,197,818,93,203,246
        56,149,776,683,350,448,693,944,190,320,469,930,84,699,785,102,583,404,674,55
        63,774,490,586,726,658,927,348,723,55,85,119,652,675,931,198,136,51,843,987
        449,717,887,154,322,428,422,150,707,846,940,933,181,713,438,144,256,439,570,661
        482,112,729,935,539,700,843,301,264,61,409,606,762,681,645,442,785,140,705,314
        884,489,162,705,493,605,149,454,715,702,328,726,846,720,843,267,321,685,197,578
        596,668,776,345,820,692,711,453,494,816,870,432,868,636,694,101,792,538,766,313
        352,306,304,850,634,782,914,420,818,435,577,112,92,944,236,614,85,817,324,439
        577,443,718,323,301,685,932,115,526,544,864,915,215,945,838,342,654,57,252,490
        298,194,449,464,540,266,901,594,582,391,779,494,590,881,763,539,710,729,925,307
        136,534,242,840,52,186,694,937,349,160,307,271,595,525,527,319,773,604,821,628
        199,711,871,255,765,257,84,527,697,142,637,775,723,364,350,106,180,845,532,258
        880,581,323,314,390,197,144,857,116,448,765,429,406,576,299,574,583,709,711,193
        690,262,700,423,785,947,687,825,206,265,580,346,245,525,728,138,601,762,432,193
        137,844,469,843,89,869,541,638,364,312,183,795,920,320,949,880,914,853,872,353
        871,470,732,492,472,666,299,127,389,424,722,715,636,937,563,155,319,322,601,761
        306,779,639,678,446,789,326,147,519,646,528,712,99,397,85,274,310,738,360,363
        58,302,245,674,323,146,776,795,887,364,238,533,254,651,602,467,730,725,690,511
        246,583,160,931,520,160,728,859,90,718,123,207,304,440,652,848,311,486,86,260
        234,948,596,116,311,840,703,933,521,537,918,202,275,628,421,740,85,543,940,111
        588,59,712,479,718,421,634,880,349,938,327,645,664,538,204,832,540,58,838,312
        772,404,254,676,602,484,345,131,921,429,602,669,523,139,884,84,105,632,841,187
        351,226,470,727,648,259,462,248,772,449,682,914,867,89,137,258,326,716,935,392
        155,783,717,523,526,347,487,790,146,871,114,582,476,398,852,141,646,931,502,204
        821,835,115,658,448,656,406,522,884,855,921,591,131,398,887,320,148,494,568,518
        873,633,883,935,163,245,60,922,95,51,600,662,394,858,645,851,687,603,306,315
        875,128,923,60,393,730,601,820,130,864,928,768,85,874,915,329,849,574,424,127
        366,447,97,54,470,859,328,330,932,442,86,321,138,704,256,594,940,768,537,546
        726,207,661,475,58,469,721,475,395,849,161,106,106,710,822,762,817,521,443,418
        868,839,438,462,51,656,750,781,257,424,16,522,664,155,437,321,420,541,649,262
        464,934,263,657,732,839,152,515,674,870,631,133,435,137,601,105,301,819,454,201
        298,84,667,149,434,179,108,275,727,262,301,274,643,107,713,714,608,943,118,487
        183,364,885,842,846,939,301,630,541,852,781,92,88,579,442,853,634,242,642,668
        590,324,137,702,793,142,102,108,819,398,524,328,80,315,263,920,593,839,593,263
        638,56,205,108,776,542,391,72,391,918,714,490,663,762,586,648,248,545,345,352
        143,837,571,194,572,435,305,351,157,196,573,488,780,191,946,61,206,692,364,332
        463,483,591,870,486,727,591,869,917,865,829,648,704,940,63,303,781,600,116,263
        90,923,266,822,253,398,13,345,589,590,205,53,194,61,438,878,137,786,567,656
        656,317,657,715,793,488,144,55,533,568,323,573,571,264,584,141,188,900,195,914
        482,635,842,728,119,138,622,764,526,947,573,571,106,238,242,132,669,940,445,884
        710,200,939,539,633,113,471,308,364,134,494,299,274,555,322,848,245,761,729,570
        451,750,656,242,325,922,654,399,351,846,334,103,792,750,138,116,475,260,521,347
        732,659,783,332,718,926,777,314,61,96,392,917,350,683,160,886,666,569,668,687
        96,572,761,846,447,529,149,251,729,137,130,686,395,898,159,200,94,160,395,946
        575,497,399,196,839,362,691,193,539,55,693,583,103,489,308,929,160,189,736,438
        303,546,583,434,634,649,146,724,532,594,450,870,748,250,784,881,641,389,676,440
        692,578,721,155,639,578,55,337,682,324,422,645,925,587,482,308,853,782,673,592
        581,83,390,633,257,149,458,190,732,629,602,683,569,571,54,155,870,791,856,528
        442,850,243,698,635,592,677,316,102,463,102,433,89,887,93,634,243,922,907,394
        445,521,880,629,793,875,68,395,151,314,60,329,346,639,521,709,633,652,137,327
        298,481,576,712,207,672,86,645,257,704,136,595,684,503,888,128,392,200,188,111
        259,310,936,250,155,62,869,870,467,817,114,445,485,527,595,105,922,709,15,653
        455,162,364,89,531,512,683,700,877,687,845,817,466,351,478,719,254,50,520,701
        330,298,55,453,306,553,107,236,818,781,717,629,774,108,466,669,148,429,648,55
        98,347,887,131,889,427,366,273,866,119,858,717,939,147,202,703,852,533,669,530
        493,567,943,632,478,6,443,360,524,734,201,877,423,155,486,250,299,431,852,344
        594,726,360,429,472,438,770,300,351,299,726,838,644,274,676,364,883,311,856,694
        838,131,241,484,638,857,174,490,325,735,520,857,465,190,319,631,160,715,924,602
        334,437,541,482,471,118,311,89,570,703,323,131,708,474,351,571,940,525,875,530
        660,19,583,534,524,718,113,161,256,353,777,884,673,57,845,197,159,256,395,918
        575,649,301,716,568,121,539,320,353,392,343,351,64,127,240,249,578,702,946,131
        855,561,259,235,763,205,159,56,791,314,856,471,352,537,860,856,149,328,306,721
        234,519,146,448,682,521,798,528,697,543,526,714,208,489,703,493,195,195,430,875
        592,653,666,448,364,601,635,459,575,264,686,452,944,449,685,425,318,366,601,237
        721,733,919,545,193,631,359,346,822,588,431,921,660,444,421,773,878,787,774,491
        155,448,86,723,642,773,522,760,571,640,837,124,244,668,657,352,736,488,597,663
        390,527,446,979,109,862,105,235,869,584,730,433,440,945,199,485,843,448,396,725
        943,869,99,62,872,868,524,477,61,536,398,848,430,674,123,652,194,871,301,424
        837,868,346,83,87,931,273,837,923,480,843,608,732,874,581,847,436,525,592,159
        396,239,581,310,478,932,64,553,764,793,772,424,874,821,790,204,718,713,697,572
        83,776,420,190,446,606,852,437,716,151,114,463,789,185,352,528,526,83,104,426
        635,150,574,329,588,469,149,474,327,428,892,115,362,439,486,453,61,450,398,437
        60,680,820,877,204,928,89,936,432,304,102,858,560,781,677,645,308,452,392,104
        248,493,56,526,487,840,261,281,134,935,130,663,788,110,317,347,199,864,940,586
        398,680,468,879,236,345,609,424,584,349,749,587,398,64,300,234,298,942,141,839
        872,645,304,326,794,148,16,200,427,773,876,258,703,102,140,255,691,778,450,678
        735,526,453,323,144,934,690,578,433,836,64,884,794,139,678,275,397,945,692,18
        92,392,762,670,319,727,235,146,363,795,710,84,393,23,350,427,494,835,463,193
        677,87,698,254,259,702,850,114,265,792,434,366,260,464,527,392,519,838,922,2
        59,208,241,311,145,584,681,595,943,880,575,917,521,684,739,263,327,323,261,53
        682,735,790,735,481,591,103,815,721,696,432,578,106,835,134,530,391,819,788,50
    """.trimIndent()

    private class Rule(val name: String, val ranges: List<IntRange>) {
        fun isValid(value: Int): Boolean = ranges.any { value in it }
        override fun toString(): String {
            return "Rule(name='$name', range=$ranges)"
        }
    }

    private class Ticket(val values: List<Int>) {
        fun contains(value: Int): Boolean = values.contains(value)
        override fun toString(): String {
            return "Ticket(values=$values)"
        }
    }
}