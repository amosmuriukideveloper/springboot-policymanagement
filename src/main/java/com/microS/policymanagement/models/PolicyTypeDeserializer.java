package com.microS.policymanagement.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PolicyTypeDeserializer extends JsonDeserializer<PolicyType> {

        @Override
        public PolicyType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String policyTypeString = jsonParser.getText();
            return PolicyType.valueOf(policyTypeString.toUpperCase());
        }
    }

