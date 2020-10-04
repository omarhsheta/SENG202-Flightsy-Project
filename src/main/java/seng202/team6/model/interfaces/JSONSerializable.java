package seng202.team6.model.interfaces;

/**
 * Indicates that this class is serializable into JSON and back
 */
public interface JSONSerializable {
    /**
     * Convert object to json
     * @return Json string
     */
    String ToJson();
}
