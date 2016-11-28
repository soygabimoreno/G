package com.gabrielmorenoibarra.g.java;

import java.util.HashMap;
import java.util.Map;

/**
 * Tool for getting the country ISO from country code and vice versa.
 * Created by Gabriel Moreno on 2015-11-26.
 */
public class GISO {

    private Map<String, String> iso2Code;

    public GISO() {
        iso2Code = new HashMap<>();
        iso2Code.put("AF", "+93");
        iso2Code.put("AL", "+355");
        iso2Code.put("DZ", "+213");
        iso2Code.put("AS", "+1684");
        iso2Code.put("AD", "+376");
        iso2Code.put("AO", "+244");
        iso2Code.put("AI", "+1264");
        iso2Code.put("AG", "+1268");
        iso2Code.put("AR", "+54");
        iso2Code.put("AM", "+374");
        iso2Code.put("AW", "+297");
        iso2Code.put("AU", "+61");
        iso2Code.put("AT", "+43");
        iso2Code.put("AZ", "+994");
        iso2Code.put("BS", "+1242");
        iso2Code.put("BH", "+973");
        iso2Code.put("BD", "+880");
        iso2Code.put("BB", "+1246");
        iso2Code.put("BY", "+375");
        iso2Code.put("BE", "+32");
        iso2Code.put("BZ", "+501");
        iso2Code.put("BJ", "+229");
        iso2Code.put("BM", "+1441");
        iso2Code.put("BT", "+975");
        iso2Code.put("BO", "+591");
        iso2Code.put("BA", "+387");
        iso2Code.put("BW", "+267");
        iso2Code.put("BR", "+55");
        iso2Code.put("BN", "+673");
        iso2Code.put("BG", "+359");
        iso2Code.put("BF", "+226");
        iso2Code.put("BI", "+257");
        iso2Code.put("KH", "+855");
        iso2Code.put("CM", "+237");
        iso2Code.put("CA", "+1");
        iso2Code.put("CV", "+238");
        iso2Code.put("KY", "+1345");
        iso2Code.put("CF", "+236");
        iso2Code.put("TD", "+235");
        iso2Code.put("CL", "+56");
        iso2Code.put("CN", "+86");
        iso2Code.put("CO", "+57");
        iso2Code.put("KM", "+269");
        iso2Code.put("CK", "+682");
        iso2Code.put("CR", "+506");
        iso2Code.put("HR", "+385");
        iso2Code.put("CU", "+53");
        iso2Code.put("CY", "+357");
        iso2Code.put("CZ", "+420");
        iso2Code.put("CD", "+243");
        iso2Code.put("DK", "+45");
        iso2Code.put("DJ", "+253");
        iso2Code.put("DM", "+1767");
        iso2Code.put("DO", "+1");
        iso2Code.put("TL", "+670");
        iso2Code.put("EC", "+593");
        iso2Code.put("EG", "+20");
        iso2Code.put("SV", "+503");
        iso2Code.put("GQ", "+240");
        iso2Code.put("ER", "+291");
        iso2Code.put("EE", "+372");
        iso2Code.put("ET", "+251");
        iso2Code.put("FO", "+298");
        iso2Code.put("FJ", "+679");
        iso2Code.put("FI", "+358");
        iso2Code.put("FR", "+33");
        iso2Code.put("GF", "+594");
        iso2Code.put("PF", "+689");
        iso2Code.put("GA", "+241");
        iso2Code.put("GM", "+220");
        iso2Code.put("GE", "+995");
        iso2Code.put("DE", "+49");
        iso2Code.put("GH", "+233");
        iso2Code.put("GI", "+350");
        iso2Code.put("GR", "+30");
        iso2Code.put("GL", "+299");
        iso2Code.put("GD", "+1473");
        iso2Code.put("GP", "+590");
        iso2Code.put("GU", "+1671");
        iso2Code.put("GT", "+502");
        iso2Code.put("GN", "+224");
        iso2Code.put("GW", "+245");
        iso2Code.put("GY", "+592");
        iso2Code.put("HT", "+509");
        iso2Code.put("HN", "+504");
        iso2Code.put("HK", "+852");
        iso2Code.put("HU", "+36");
        iso2Code.put("IS", "+354");
        iso2Code.put("IN", "+91");
        iso2Code.put("ID", "+62");
        iso2Code.put("IR", "+98");
        iso2Code.put("IQ", "+964");
        iso2Code.put("IE", "+353");
        iso2Code.put("IL", "+972");
        iso2Code.put("IT", "+39");
        iso2Code.put("CI", "+225");
        iso2Code.put("JM", "+1876");
        iso2Code.put("JP", "+81");
        iso2Code.put("JO", "+962");
        iso2Code.put("KZ", "+7");
        iso2Code.put("KE", "+254");
        iso2Code.put("KI", "+686");
        iso2Code.put("KW", "+965");
        iso2Code.put("KG", "+996");
        iso2Code.put("LA", "+856");
        iso2Code.put("LV", "+371");
        iso2Code.put("LB", "+961");
        iso2Code.put("LS", "+266");
        iso2Code.put("LR", "+231");
        iso2Code.put("LY", "+218");
        iso2Code.put("LI", "+423");
        iso2Code.put("LT", "+370");
        iso2Code.put("LU", "+352");
        iso2Code.put("MO", "+853");
        iso2Code.put("MK", "+389");
        iso2Code.put("MG", "+261");
        iso2Code.put("MW", "+265");
        iso2Code.put("MY", "+60");
        iso2Code.put("MV", "+960");
        iso2Code.put("ML", "+223");
        iso2Code.put("MT", "+356");
        iso2Code.put("MQ", "+596");
        iso2Code.put("MR", "+222");
        iso2Code.put("MU", "+230");
        iso2Code.put("MX", "+52");
        iso2Code.put("FM", "+691");
        iso2Code.put("MD", "+373");
        iso2Code.put("MC", "+377");
        iso2Code.put("MN", "+976");
        iso2Code.put("ME", "+382");
        iso2Code.put("MS", "+1664");
        iso2Code.put("MA", "+212");
        iso2Code.put("MZ", "+258");
        iso2Code.put("MM", "+95");
        iso2Code.put("NA", "+264");
        iso2Code.put("NR", "+674");
        iso2Code.put("NP", "+977");
        iso2Code.put("NL", "+31");
        iso2Code.put("AN", "+599");
        iso2Code.put("NC", "+687");
        iso2Code.put("NZ", "+64");
        iso2Code.put("NI", "+505");
        iso2Code.put("NE", "+227");
        iso2Code.put("NG", "+234");
        iso2Code.put("KP", "+850");
        iso2Code.put("MP", "+1670");
        iso2Code.put("NO", "+47");
        iso2Code.put("OM", "+968");
        iso2Code.put("PK", "+92");
        iso2Code.put("PS", "+970");
        iso2Code.put("PA", "+507");
        iso2Code.put("PG", "+675");
        iso2Code.put("PY", "+595");
        iso2Code.put("PE", "+51");
        iso2Code.put("PH", "+63");
        iso2Code.put("PL", "+48");
        iso2Code.put("PT", "+351");
        iso2Code.put("PR", "+1");
        iso2Code.put("QA", "+974");
        iso2Code.put("CG", "+242");
        iso2Code.put("RE", "+262");
        iso2Code.put("RO", "+40");
        iso2Code.put("RU", "+7");
        iso2Code.put("RW", "+250");
        iso2Code.put("KN", "+1869");
        iso2Code.put("LC", "+1758");
        iso2Code.put("PM", "+508");
        iso2Code.put("VC", "+1784");
        iso2Code.put("WS", "+685");
        iso2Code.put("SM", "+378");
        iso2Code.put("ST", "+239");
        iso2Code.put("SA", "+966");
        iso2Code.put("SN", "+221");
        iso2Code.put("RS", "+381");
        iso2Code.put("SC", "+248");
        iso2Code.put("SL", "+232");
        iso2Code.put("SG", "+65");
        iso2Code.put("SK", "+421");
        iso2Code.put("SI", "+386");
        iso2Code.put("SB", "+677");
        iso2Code.put("SO", "+252");
        iso2Code.put("ZA", "+27");
        iso2Code.put("KR", "+82");
        iso2Code.put("SS", "+211");
        iso2Code.put("ES", "+34");
        iso2Code.put("LK", "+94");
        iso2Code.put("SD", "+249");
        iso2Code.put("SR", "+597");
        iso2Code.put("SZ", "+268");
        iso2Code.put("SE", "+46");
        iso2Code.put("CH", "+41");
        iso2Code.put("SY", "+963");
        iso2Code.put("TW", "+886");
        iso2Code.put("TJ", "+992");
        iso2Code.put("TZ", "+255");
        iso2Code.put("TH", "+66");
        iso2Code.put("TG", "+228");
        iso2Code.put("TO", "+676");
        iso2Code.put("TT", "+1868");
        iso2Code.put("TN", "+216");
        iso2Code.put("TR", "+90");
        iso2Code.put("TM", "+993");
        iso2Code.put("TC", "+1649");
        iso2Code.put("UG", "+256");
        iso2Code.put("UA", "+380");
        iso2Code.put("AE", "+971");
        iso2Code.put("GB", "+44");
        iso2Code.put("US", "+1");
        iso2Code.put("UY", "+598");
        iso2Code.put("UZ", "+998");
        iso2Code.put("VU", "+678");
        iso2Code.put("VE", "+58");
        iso2Code.put("VN", "+84");
        iso2Code.put("VG", "+1340");
        iso2Code.put("VI", "+1284");
        iso2Code.put("YE", "+967");
        iso2Code.put("ZM", "+260");
        iso2Code.put("ZW", "+263");
    }

    /**
     * @param countryIso The country ISO.
     * @return the country code from the country ISO.
     */
    public String iso2Code(String countryIso) {
        return iso2Code.get(countryIso.toUpperCase()); // We set the String to capital letters just in case
    }

    /**
     * @param countryCode The country code.
     * @return the country ISO from the country code.
     */
    public String code2Iso(String countryCode) {
        for (String s : iso2Code.keySet()) {
            if (iso2Code.get(s).equals(countryCode)) {
                return s;
            }
        }
        return null; // The countryCode doesn't exist in the HashMap
    }

    public Map<String, String> getAll() {
        return iso2Code;
    }
}