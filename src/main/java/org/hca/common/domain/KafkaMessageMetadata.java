package org.hca.common.domain;

public class KafkaMessageMetadata {

    private String facilityId;
    private String facilityName;
    private String elasticIndex;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getElasticIndex() {
        return elasticIndex;
    }

    public void setElasticIndex(String elasticIndex) {
        this.elasticIndex = elasticIndex;
    }
}
