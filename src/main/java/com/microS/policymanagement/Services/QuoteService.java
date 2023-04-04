//package com.microS.policymanagement.Services;
//
//import com.microS.policymanagement.models.Quote;
//import org.springframework.stereotype.Service;
//
//@Service
//public class QuoteService {
//    public Quote generateQuote(String vehicle, String policy, String coverageType, String coverageOption) {
//        // calculate coverageTypePremium, basePremium, levies and totalPremium based on inputs
//        double coverageTypePremium = 0.0;
//        double basePremium = 0.0;
//        double levies = 0.0;
//        double totalPremium = coverageTypePremium + basePremium + levies;
//
//        Quote quote = new Quote();
//        quote.setVehicle(quote.getVehicle());
//        quote.setPolicy(quote.getPolicy());
//        quote.setCoverageType(quote.getCoverageType());
//        quote.setCoverageOptions(quote.getCoverageOptions());
//        quote.setCoverageTypePremium(quote.getCoverageTypePremium());
//        quote.setBasePremium(basePremium);
//        quote.setLevies(levies);
//        quote.setTotalPremium(totalPremium);
//
//        return quote;
//    }
//}
