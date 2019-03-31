package ro.pub.cs.systems.eim.practicaltest01;

interface Constants {
    public static final String LEFT_COUNT = "left count";
    public static final String RIGHT_COUNT = "right count";
    public static final String TOTAL_COUNT = "total count";
    public static final int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.arithmeticmean",
            "ro.pub.cs.systems.eim.practicaltest01.geometricmean"
    };


    public final String MESSAGE = "service_message";
    public final int PRAG = 10;
    public final int SERVICE_STOPPED = 0;
    public final int SERVICE_STARTED = 1;
    public final String FIRSTNUMBER = "first number";
    public final String SECONDNUMBER = "second number";
}
