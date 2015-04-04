package mho.wheels.iterables;

import mho.wheels.testing.Testing;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static mho.wheels.iterables.IterableUtils.charsToString;
import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RandomProviderTest {
    private RandomProvider P;

    @Before
    public void initialize() {
        P = new RandomProvider(new Random(-1781048559808000493L));
    }

    @Test
    public void testBooleans() {
        aeqit(take(20, P.booleans()), "[true, true, true, true, false, true, true, false, true, false," +
                                      " false, false, false, true, true, true, true, false, true, false]");
    }

    @Test
    public void testOrderings() {
        aeqit(take(20, P.orderings()),
                "[EQ, EQ, LT, LT, LT, EQ, LT, GT, GT, GT, LT, GT, LT, LT, GT, LT, LT, EQ, EQ, LT]");
    }

    @Test
    public void testRoundingModes() {
        aeqit(take(20, P.roundingModes()),
                "[UNNECESSARY, HALF_EVEN, UP, HALF_DOWN, UP, FLOOR, HALF_DOWN, FLOOR, CEILING, CEILING," +
                " CEILING, UP, UNNECESSARY, CEILING, HALF_UP, HALF_UP, HALF_DOWN, UP, UNNECESSARY, HALF_DOWN]");
    }

    @Test
    public void testPositiveBytes() {
        aeqit(take(20, P.positiveBytes()),
                "[18, 2, 37, 20, 96, 86, 89, 16, 60, 41, 96, 31, 22, 115, 38, 103, 97, 73, 31, 39]");
    }

    @Test
    public void testPositiveShorts() {
        aeqit(take(20, P.positiveShorts()),
                "[29559, 24497, 25521, 7601, 15393, 17782, 25443, 13977, 19844, 10977," +
                " 6993, 15895, 13568, 24091, 18433, 27279, 26356, 29039, 23271, 17273]");
    }

    @Test
    public void testPositiveIntegers() {
        aeqit(take(20, P.positiveIntegers()),
                "[1749459689, 2143674404, 1412152153, 1557652480, 27277537, 1405165043, 1262439652, 761846727," +
                " 1823634462, 594633726, 589649158, 736945725, 167878909, 1124489230, 1673560191, 1856081227," +
                " 1324337428, 166157729, 2076599129, 1037453260]");
    }

    @Test
    public void testPositiveLongs() {
        aeqit(take(20, P.positiveLongs()),
                "[3418999782456442809, 6316449450962204674, 234312252881255396, 7602470043748550772," +
                " 2781843328518141957, 5065047696626797689, 1442068837050396699, 4070971502122296683," +
                " 7070772197709661375, 608893388310409322, 471066488414342743, 8378098296417551167," +
                " 3854184673538224894, 2723679502578984382, 3297591106051422579, 5761072552197714005," +
                " 8575917774971103912, 8229809756225242051, 351898943428221388, 2417358956864889798]");
    }

    @Test
    public void testPositiveBigIntegers_Int() {
        aeqit(take(20, P.positiveBigIntegers(3)), "[15, 1, 7, 3, 1, 2, 8, 1, 13, 5, 20, 2, 1, 1, 1, 1, 1, 1, 3, 1]");
        aeqit(take(20, P.positiveBigIntegers(4)),
                "[1, 1, 1, 6, 4, 94, 59, 4, 1, 1, 1, 43, 15, 1, 3, 1, 2, 103103, 393, 12]");
        aeqit(take(20, P.positiveBigIntegers(5)),
                "[1, 2, 2821, 1, 13, 1, 273, 1, 3, 3, 1, 3, 15, 2, 6, 14, 5, 7, 1, 1]");
        aeqit(take(20, P.positiveBigIntegers(10)),
                "[418, 1, 886, 15, 2, 1023538995542242, 2527383, 11, 2, 3411," +
                " 10, 4891, 8, 2, 25, 3, 10, 349, 110732294, 3877]");
        aeqit(take(20, P.positiveBigIntegers(100)),
                "[631847851262602872164, 62178362933629457256170097449498832870026795417, 547758176," +
                " 2346149950119691144404, 311, 4742738, 67302549518065217887062796935441749979, 53471, 4223," +
                " 17312403, 316463874199, 6, 447122575, 1176, 704610823827," +
                " 31430331193008341986440693101333088795173295035345951291600655076040609838446721240723225651953502" +
                "51261283498014102904063, 7517586777550828054626795662503, 741109, 101419744017795180979313623318," +
                " 25612091393]");
        try {
            P.positiveBigIntegers(2);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.positiveBigIntegers(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.positiveBigIntegers(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testPositiveBigIntegers() {
        aeqit(take(20, P.positiveBigIntegers()),
                "[65649474733, 50, 1752003, 108680047959250986, 2, 169829217569110637456607575012447814909456," +
                " 8046132249267142822265255, 78549137, 3080," +
                " 6955247343603701669934693326084685760295830262297267296665, 547758176, 2133810949," +
                " 547945394950967, 4742738, 27183283269, 1631119, 1811559053982367, 595931, 13367, 20607]");
    }

    @Test
    public void testNegativeBytes() {
        aeqit(take(20, P.negativeBytes()),
                "[-105, -128, -85, -93, -2, -84, -76, -46, -109, -36," +
                " -36, -44, -11, -68, -100, -111, -79, -10, -124, -62]");
    }

    @Test
    public void testNegativeShorts() {
        aeqit(take(20, P.negativeShorts()),
                "[-26695, -32710, -21548, -23768, -417, -21442, -19264, -11625, -27827, -9074," +
                " -8998, -11245, -2562, -17159, -25537, -28322, -20208, -2536, -31687, -15831]");
    }

    @Test
    public void testNegativeIntegers() {
        aeqit(take(20, P.negativeIntegers()),
                "[-796047920, -7618489, -1470662991, -1179662338, -1484637212, -1770087994, -647698373, -2045988837," +
                " -947846915, -582804843, -1646292442, -141769040, -1282994601, -213515457, -1321491010, -767780260," +
                " -1996736455, -2015128232, -1916151903, -81932857]");
    }

    @Test
    public void testNegativeLongs() {
        aeqit(take(20, P.negativeLongs()),
                "[-3418999782456442809, -6316449450962204674, -7602470043748550772, -2781843328518141957," +
                " -4070971502122296683, -7070772197709661375, -608893388310409322, -3297591106051422579," +
                " -8575917774971103912, -8229809756225242051, -351898943428221388, -5035479974893608156," +
                " -2087360320830562347, -4864443654894485421, -3626293116983244765, -6128126907599458534," +
                " -4181052272311759209, -1935017808723883567, -3861844328646811360, -196660781681668032]");
    }

    @Test
    public void testNegativeBigIntegers_Int() {
        aeqit(take(20, P.negativeBigIntegers(3)),
                "[-15, -1, -7, -3, -1, -2, -8, -1, -13, -5, -20, -2, -1, -1, -1, -1, -1, -1, -3, -1]");
        aeqit(take(20, P.negativeBigIntegers(4)),
                "[-1, -1, -1, -6, -4, -94, -59, -4, -1, -1, -1, -43, -15, -1, -3, -1, -2, -103103, -393, -12]");
        aeqit(take(20, P.negativeBigIntegers(5)),
                "[-1, -2, -2821, -1, -13, -1, -273, -1, -3, -3, -1, -3, -15, -2, -6, -14, -5, -7, -1, -1]");
        aeqit(take(20, P.negativeBigIntegers(10)),
                "[-418, -1, -886, -15, -2, -1023538995542242, -2527383, -11, -2, -3411, -10, -4891, -8, -2, -25, -3," +
                        " -10, -349, -110732294, -3877]");
        aeqit(take(20, P.negativeBigIntegers(100)),
                "[-631847851262602872164, -62178362933629457256170097449498832870026795417, -547758176," +
                " -2346149950119691144404, -311, -4742738, -67302549518065217887062796935441749979, -53471, -4223," +
                " -17312403, -316463874199, -6, -447122575, -1176, -704610823827," +
                " -3143033119300834198644069310133308879517329503534595129160065507604060983844672124072322565195350" +
                "251261283498014102904063, -7517586777550828054626795662503, -741109," +
                " -101419744017795180979313623318, -25612091393]");
        try {
            P.negativeBigIntegers(2);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.negativeBigIntegers(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.negativeBigIntegers(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testNegativeBigIntegers() {
        aeqit(take(20, P.negativeBigIntegers()),
                "[-65649474733, -50, -1752003, -108680047959250986, -2, -169829217569110637456607575012447814909456," +
                " -8046132249267142822265255, -78549137, -3080," +
                " -6955247343603701669934693326084685760295830262297267296665, -547758176, -2133810949," +
                " -547945394950967, -4742738, -27183283269, -1631119, -1811559053982367, -595931, -13367, -20607]");
    }

    @Test
    public void testNaturalBytes() {
        aeqit(take(20, P.naturalBytes()),
                "[80, 71, 49, 126, 65, 100, 70, 12, 59, 123, 11, 121, 120, 27, 125, 21, 38, 65, 48, 22]");
    }

    @Test
    public void testNaturalShorts() {
        aeqit(take(20, P.naturalShorts()),
                "[17872, 16455, 30385, 18430, 29121, 15332, 6598, 14220, 26683, 18427," +
                " 10763, 19577, 16888, 12315, 253, 6805, 4646, 15169, 18096, 3990]");
    }

    @Test
    public void testNaturalIntegers() {
        aeqit(take(20, P.naturalIntegers()),
                "[1351435728, 2139865159, 676820657, 967821310, 54555073, 662846436, 377395654, 1523693452," +
                " 1499785275, 1189267451, 1179298315, 1473891449, 335757816, 101494811, 1199636733, 1564678805," +
                " 501191206, 332315457, 2005714608, 2074906518]");
    }

    @Test
    public void testNaturalLongs() {
        aeqit(take(20, P.naturalLongs()),
                "[5804372254398332999, 2906922585892571134, 234312252881255396, 1620901993106225036," +
                " 6441528708336633851, 5065047696626797689, 1442068837050396699, 5152400534732479125," +
                " 2152599839145114433, 8614478648544366486, 471066488414342743, 8378098296417551167," +
                " 3854184673538224894, 2723679502578984382, 5925780930803353229, 5761072552197714005," +
                " 647454261883671896, 993562280629533757, 8871473093426554420, 2417358956864889798]");
    }

    @Test
    public void testNaturalBigIntegers_Int() {
        aeqit(take(20, P.naturalBigIntegers(3)),
                "[7, 0, 3, 1, 0, 0, 0, 0, 5, 1, 4, 0, 0, 0, 0, 0, 0, 0, 1, 0]");
        aeqit(take(20, P.naturalBigIntegers(4)),
                "[0, 0, 0, 2, 0, 30, 27, 0, 0, 0, 0, 11, 7, 0, 1, 0, 0, 37567, 137, 4]");
        aeqit(take(20, P.naturalBigIntegers(5)),
                "[0, 0, 773, 0, 5, 0, 17, 0, 1, 1, 0, 1, 7, 0, 2, 6, 1, 3, 0, 0]");
        aeqit(take(20, P.naturalBigIntegers(10)),
                "[162, 0, 374, 7, 0, 460589042120930, 430231, 3, 0, 1363, 2, 795, 0, 0, 9, 1, 2, 93, 43623430, 1829]");
        aeqit(take(20, P.naturalBigIntegers(100)),
                "[41552040903897220452, 16506436767038741062304946427114988505778903449, 10887264," +
                " 1165558329402279840980, 55, 548434, 24767253652947909954140971006470723547, 20703, 127, 535187," +
                " 41585967255, 2, 178687119, 152, 154855009939," +
                " 56078324121392560898815013813029700518762371070537161632940615106341336182783092944267721191521241" +
                "9825380326041355410687, 2446984376637910448639982840999, 216821, 22191581503530843385769672982," +
                " 8432222209]");
        try {
            P.naturalBigIntegers(2);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.naturalBigIntegers(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.naturalBigIntegers(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testNaturalBigIntegers() {
        aeqit(take(20, P.naturalBigIntegers()),
                "[31289736365, 18, 703427, 36622453921323050, 0, 82716931637350390809983675509915152776720," +
                " 3210428970808626123440551, 11440273, 1032," +
                " 678145608217020906098903902877019344193474817833232783769, 10887264, 1060069125, 266470418240311," +
                " 548434, 10003414085, 582543, 685659147139743, 71643, 5175, 4223]");
    }

    @Test
    public void testBytes() {
        aeqit(take(20, P.bytes()),
                "[-48, 71, -79, -2, -63, -28, -58, -116, 59, -5, 11, 121, -8, 27, -3, -107, 38, 65, -80, -106]");
    }

    @Test
    public void testShorts() {
        aeqit(take(20, P.shorts()),
                "[17872, -16313, 30385, -14338, 29121, 15332, -26170, -18548, -6085, -14341," +
                " -22005, -13191, 16888, -20453, 253, 6805, -28122, -17599, -14672, -28778]");
    }

    @Test
    public void testIntegers() {
        aeqit(take(20, P.integers()),
                "[-796047920, -7618489, -1470662991, -1179662338, 54555073, -1484637212, -1770087994, 1523693452," +
                " -647698373, 1189267451, 1179298315, 1473891449, 335757816, -2045988837, -947846915, -582804843," +
                " -1646292442, 332315457, -141769040, 2074906518]");
    }

    @Test
    public void testLongs() {
        aeqit(take(20, P.longs()),
                "[-3418999782456442809, -6316449450962204674, 234312252881255396, -7602470043748550772," +
                " -2781843328518141957, 5065047696626797689, 1442068837050396699, -4070971502122296683," +
                " -7070772197709661375, -608893388310409322, 471066488414342743, 8378098296417551167," +
                " 3854184673538224894, 2723679502578984382, -3297591106051422579, 5761072552197714005," +
                " -8575917774971103912, -8229809756225242051, -351898943428221388, 2417358956864889798]");
    }

    @Test
    public void testBigIntegers_Int() {
        aeqit(take(20, P.bigIntegers(3)), "[7, -1, 1, -1, 0, 0, -1, -6, -1, 6, -1, 0, -1, 0, -1, 1, 1, 1, 0, -1]");
        aeqit(take(20, P.bigIntegers(4)), "[1, 1, -7, 4, -2, 0, 10, -1, 0, 0, -2, -8, -6, 3, 0, -1, 0, 0, -2, 4]");
        aeqit(take(20, P.bigIntegers(5)),
                "[1, 773, 2, 0, 24, -10, -1, -2, -1, 1, -3, 3, -2, 7, 10, 2, 3271, 120, 11, 0]");
        aeqit(take(20, P.bigIntegers(10)),
                "[-2, 11, -454, -19342463128, -3412, 13, -1, -55, 0, -4," +
                " 0, 3, 35, -1, -43623431, -8, 0, 19579, -29, 4]");
        aeqit(take(20, P.bigIntegers(100)),
                "[-88557569903630551599799955827784349169626451040329715964314, 202, 60318599134," +
                " 1640702634687943479, -61191085979970053457695, 4254037577138942334193887, 12821954296221206544535," +
                " -1638087117977, 3, 582, 230, 16168191, 26, 51481126197039749041591204, -71523839508501956928333," +
                " 1325372505506602807026564, 3757547800543576, 4364599426705721714," +
                " 113847612089673464000064561451248807, -400979282943760427063214761070268927754993666]");
        try {
            P.bigIntegers(2);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.bigIntegers(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.bigIntegers(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testBigIntegers() {
        aeqit(take(20, P.bigIntegers()),
                "[31289736365, 1332686935725045463947306, -49775, -12910780249752364756422," +
                " -23944809563965594065693683811078439336, 0, 320784164," +
                " -88557569903630551599799955827784349169626451040329715964314, 202, 60318599134," +
                " 1640702634687943479, 30595542989985026728847, -1, -1063509394284735583548472, 1," +
                " 12821954296221206544535, 819043558988, -1, 3, 582]");
    }

    @Test
    public void testNaturalIntegersGeometric() {
        aeqit(take(20, P.naturalIntegersGeometric(2)), "[2, 2, 0, 3, 0, 0, 0, 1, 3, 0, 0, 0, 2, 7, 4, 0, 0, 0, 0, 0]");
        aeqit(take(20, P.naturalIntegersGeometric(3)), "[2, 0, 7, 1, 0, 2, 9, 4, 3, 2, 0, 0, 0, 0, 4, 3, 0, 2, 0, 0]");
        aeqit(take(20, P.naturalIntegersGeometric(4)), "[5, 2, 1, 18, 0, 4, 7, 4, 3, 1, 0, 2, 1, 0, 5, 6, 1, 0, 8, 3]");
        aeqit(take(20, P.naturalIntegersGeometric(5)),
                "[0, 4, 0, 7, 1, 1, 2, 3, 0, 1, 1, 3, 1, 12, 0, 5, 13, 1, 14, 6]");
        aeqit(take(20, P.naturalIntegersGeometric(10)),
                "[12, 26, 5, 2, 3, 14, 2, 19, 5, 8, 13, 11, 6, 1, 5, 5, 1, 2, 1, 11]");
        aeqit(take(20, P.naturalIntegersGeometric(100)),
                "[222, 37, 133, 281, 103, 43, 193, 21, 16, 3, 7, 96, 151, 39, 5, 76, 173, 25, 82, 70]");
        try {
            P.naturalIntegersGeometric(1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.naturalIntegersGeometric(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.naturalIntegersGeometric(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testPositiveIntegersGeometric() {
        aeqit(take(20, P.positiveIntegersGeometric(3)), "[3, 3, 1, 4, 1, 1, 1, 2, 4, 1, 1, 1, 3, 8, 5, 1, 1, 1, 1, 1]");
        aeqit(take(20, P.positiveIntegersGeometric(4)), "[3, 1, 8, 2, 1, 3, 10, 5, 4, 3, 1, 1, 1, 1, 5, 4, 1, 3, 1, 1]");
        aeqit(take(20, P.positiveIntegersGeometric(5)), "[6, 3, 2, 19, 1, 5, 8, 5, 4, 2, 1, 3, 2, 1, 6, 7, 2, 1, 9, 4]");
        aeqit(take(20, P.positiveIntegersGeometric(10)),
                "[1, 5, 1, 12, 3, 9, 19, 1, 6, 14, 24, 13, 27, 6, 3, 4, 15, 3, 5, 15]");
        aeqit(take(20, P.positiveIntegersGeometric(100)),
                "[6, 298, 38, 134, 282, 104, 44, 194, 22, 17, 4, 8, 97, 152, 40, 6, 77, 174, 26, 83]");
        try {
            P.positiveIntegersGeometric(2);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.positiveIntegersGeometric(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.positiveIntegersGeometric(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testNegativeIntegersGeometric() {
        aeqit(take(20, P.negativeIntegersGeometric(3)),
                "[-3, -3, -1, -4, -1, -1, -1, -2, -4, -1, -1, -1, -3, -8, -5, -1, -1, -1, -1, -1]");
        aeqit(take(20, P.negativeIntegersGeometric(4)),
                "[-3, -1, -8, -2, -1, -3, -10, -5, -4, -3, -1, -1, -1, -1, -5, -4, -1, -3, -1, -1]");
        aeqit(take(20, P.negativeIntegersGeometric(5)),
                "[-6, -3, -2, -19, -1, -5, -8, -5, -4, -2, -1, -3, -2, -1, -6, -7, -2, -1, -9, -4]");
        aeqit(take(20, P.negativeIntegersGeometric(10)),
                "[-1, -5, -1, -12, -3, -9, -19, -1, -6, -14, -24, -13, -27, -6, -3, -4, -15, -3, -5, -15]");
        aeqit(take(20, P.negativeIntegersGeometric(100)),
                "[-6, -298, -38, -134, -282, -104, -44, -194, -22, -17," +
                " -4, -8, -97, -152, -40, -6, -77, -174, -26, -83]");
        try {
            P.negativeIntegersGeometric(2);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.negativeIntegersGeometric(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.negativeIntegersGeometric(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testIntegersGeometric() {
        aeqit(take(20, P.integersGeometric(2)),
                "[-3, 0, 0, 2, 0, -2, -2, -2, 0, -1, -1, -1, -1, -2, -5, -3, 0, -2, 0, -1]");
        aeqit(take(20, P.integersGeometric(3)),
                "[1, 2, 4, 1, -3, 2, -6, -5, -3, 7, -3, -3, -1, 0, 0, 3, 2, -3, -1, 3]");
        aeqit(take(20, P.integersGeometric(4)),
                "[13, 6, -6, 5, 1, 6, -1, 7, 3, 1, 8, -5, 3, 1, -6, -2, -1, -2, 3, -6]");
        aeqit(take(20, P.integersGeometric(5)),
                "[1, 4, 8, -8, 11, 1, -14, -6, -2, -3, 6, 2, -1, -1, -3, 2, -10, 1, -1, 5]");
        aeqit(take(20, P.integersGeometric(10)),
                "[-1, 4, 12, -1, -1, -14, 4, 1, 2, 20, -1, 14, 2, 3, -3, 0, 3, -5, -1, 2]");
        aeqit(take(20, P.integersGeometric(100)),
                "[-231, 184, -268, 88, 13, 22, -20, 34, 157, 175, -123, 201, 93, -50, 57, -100, 159, -36, -46, 141]");
        try {
            P.integersGeometric(1);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.integersGeometric(0);
            fail();
        } catch (IllegalArgumentException ignored) {}
        try {
            P.integersGeometric(-4);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testAsciiCharacters() {
        aeq(charsToString(take(100, P.asciiCharacters())),
                "PG1~AdF\f;{\13yx\33}\25&A0\26zW\3?\n~\24>\\\rvU9X!=G4*FV:3ByK$$YU\32cE" +
                "S\37#\f\32\34\27\16Q\13 \20@p:PN\t\30YDC\f\30Wp,.d72\6}K$*#`4RUYh>IE}");
    }

    @Test
    public void testCharacters() {
        aeq(charsToString(take(100, P.characters())),
                "\u45d0\uc047\u76b1\uc7fe\u71c1\u3be4\u99c6\ub78c\ue83b\uc7fb\uaa0b\ucc79\u41f8\ub01b\u00fd\u1a95" +
                "\u9226\ubb41\uc6b0\u8f96\u907a\u0e57\ufc03\u033f\ud08a\u32fe\u7414\ua5be\u9a5c\u468d\u70f6\u7055" +
                "\u3839\u9558\ud7a1\u7c3d\ucdc7\u2634\u2f2a\u63c6\u0c56\ufa3a\udf33\u11c2\u5879\u04cb\u61a4\ua324" +
                "\u34d9\u1fd5\u5b9a\u3ee3\u0645\u8053\ud19f\u0823\u850c\u6f1a\ueb1c\u8e97\u6f8e\u49d1\uf90b\u6120" +
                "\u5210\u1040\ub3f0\ua6ba\u77d0\u01ce\ue509\ue198\u5959\u0f44\ueec3\u408c\u1c18\u24d7\ue870\u22ac" +
                "\uf72e\u9064\u4137\ub132\u9186\u07fd\u1e4b\ub3a4\uad2a\u14a3\ue860\u0234\ub3d2\ue555\ubc59\u3a68" +
                "\uf8be\uce49\ub345\u99fd");
    }

    @Test
    public void testPositiveOrdinaryFloats() {
        aeqit(take(20, P.positiveOrdinaryFloats()),
                "[1.89613015E10, 1.1960635E-14, 3.3527607E-4, 5.655431E-37, 3.614718E-15, 2.0566479E-25," +
                " 2.9515041E16, 4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, 2.6460455E-35," +
                " 66049.98, 8.7866956E17, 5.9178722E-21, 5.2186357E-27, 5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    public void testNegativeOrdinaryFloats() {
        aeqit(take(20, P.negativeOrdinaryFloats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, -5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " -2.9515041E16, -4.02697717E15, -29027.99, -12970.511, -4.78944453E14, -6.62682E-27," +
                " -2.6460455E-35, -66049.98, -8.7866956E17, -5.9178722E-21, -5.2186357E-27, -5.710558E33," +
                " -1.7919747E36, -5.174596E-35]");
    }

    @Test
    public void testOrdinaryFloats() {
        aeqit(take(20, P.ordinaryFloats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, 5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " 2.9515041E16, -4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, -2.6460455E-35," +
                " -66049.98, -8.7866956E17, -5.9178722E-21, 5.2186357E-27, -5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    public void testFloats() {
        aeqit(take(20, P.floats()),
                "[-1.89613015E10, -1.1960635E-14, -3.3527607E-4, 5.655431E-37, -3.614718E-15, -2.0566479E-25," +
                " 2.9515041E16, -4.02697717E15, 29027.99, 12970.511, 4.78944453E14, 6.62682E-27, -2.6460455E-35," +
                " -66049.98, -8.7866956E17, -5.9178722E-21, 5.2186357E-27, -5.710558E33, 1.7919747E36, 5.174596E-35]");
    }

    @Test
    public void testPositiveOrdinaryDoubles() {
        aeqit(take(20, P.positiveOrdinaryDoubles()),
                "[1.0846552561567438E80, 2.38197354700265E-114, 5.149568405861E-293, 2.4985843477357602E-200," +
                " 4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, 2.8252505782194046E36," +
                " 8.566220677325717E-165, 7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, 1.4224921830272466E88, 1.4069651251380964E77," +
                " 2.1879373410803317E-265, 3.027783021197556E-242, 1.1079692399020062E285, 4.408373100689709E-147]");
    }

    @Test
    public void testNegativeOrdinaryDoubles() {
        aeqit(take(20, P.negativeOrdinaryDoubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, -5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, -4.225116780176157E30, -2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, -3.60536199254296E-277, -1.2019415773498463E252," +
                " -4.813417096972919E-51, -1.3135493453396428E-126, -1.4224921830272466E88, -1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " -4.408373100689709E-147]");
    }

    @Test
    public void testOrdinaryDoubles() {
        aeqit(take(20, P.ordinaryDoubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, 5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, -1.4224921830272466E88, 1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " 4.408373100689709E-147]");
    }

    @Test
    public void testDoubles() {
        aeqit(take(20, P.doubles()),
                "[-1.0846552561567438E80, -2.38197354700265E-114, 5.149568405861E-293, -2.4985843477357602E-200," +
                " -4.3189997713962425E122, 4.225116780176157E30, 2.860204612775291E-212, -2.8252505782194046E36," +
                " -8.566220677325717E-165, -7.422984534814424E267, 3.60536199254296E-277, 1.2019415773498463E252," +
                " 4.813417096972919E-51, 1.3135493453396428E-126, -1.4224921830272466E88, 1.4069651251380964E77," +
                " -2.1879373410803317E-265, -3.027783021197556E-242, -1.1079692399020062E285," +
                " 4.408373100689709E-147]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
