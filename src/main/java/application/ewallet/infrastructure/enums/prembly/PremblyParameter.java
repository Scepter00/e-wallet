package application.ewallet.infrastructure.enums.prembly;

public enum PremblyParameter {
    NIN_URL("/vnin"),
    APP_ID("app-id"),
    API_KEY("x-api-key"),
    ACCEPT("accept"),
    BVN_NUMBER("number"),
    NIN("number_nin"),
    NUMBER("number"),
    APPLICATION_JSON("application/json"),
    NIN_FACE_URL("/nin_w_face"),
    IMAGE("image"),
    NIN_LIVENESS_URL("/biometrics/face/liveliness_check");

    private final String value;
    PremblyParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
