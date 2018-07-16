package com.gcd.api.jsonviews;

public class TaskView {
    /** 
     * Marker for field "id"
     * Use for responses when task was succesfuly added
     */
    public static class SuccesfulAdded { }
    
    /**
     * Marker for all common cases fields.
     * "id"
     * "status" 
     */
    public static class Base {}
    
    /**
     * Marker for cases when task was added and persisted.
     * Also, use to present NOT_COMPLETED tasks
     * additional included fields:
     * "first"
     * "second"
     */
    public static class Persisted extends Base {} 
    
    /**
     * Marker for cases when task was added and successfuly completed persisted.
     * Use it to present COMPLETED status tasks
     * additional included fields:
     * "result"
     */
    public static class PersistedCompleted extends Persisted { }
    
    
    /**
     * Marker for cases when task was added and but there are errors.
     * Use it to present ERROR status tasks
     * additional included fields:
     * "error"
     */
    public static class PersistedError extends Persisted { }
    
    
    /**
     * Marker for cases when requested task does not exist.
     * additional included fields:
     * "error"
     */
    public static class NotFound extends Base { }
    
    
}
