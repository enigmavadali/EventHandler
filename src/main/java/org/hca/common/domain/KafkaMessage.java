package org.hca.common.domain;

public class KafkaMessage {

    private KafkaMessageMetadata metadata;
    private Object body;

    public KafkaMessageMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(KafkaMessageMetadata metadata) {
        this.metadata = metadata;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
