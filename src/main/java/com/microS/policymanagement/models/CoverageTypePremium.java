//package com.microS.policymanagement.models;
//
//import java.util.Objects;
//
//public class CoverageTypePremium {
//
//    private CoverageOptions coverageType;
//    private double coefficient;
//
//    public CoverageTypePremium(CoverageOptions coverageType, double coefficient) {
//        this.coverageType = coverageType;
//        this.coefficient = coefficient;
//    }
//
//    public CoverageOptions getCoverageType() {
//        return coverageType;
//    }
//
//    public double getCoefficient() {
//        return coefficient;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CoverageTypePremium that = (CoverageTypePremium) o;
//        return Double.compare(that.coefficient, coefficient) == 0 &&
//                coverageType == that.coverageType;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(coverageType, coefficient);
//    }
//}
//
//
//
//
//
//
