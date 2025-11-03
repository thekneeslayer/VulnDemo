package com.example.vulndemo;

/**
 * Main entry for the demo.
 * This only calls methods that *return strings or values* and DOES NOT execute them.
 * Use for static analysis and to learn the issue + fix.
 */
public class Main {
    public static void main(String[] args) {
        SampleService svc = new SampleService();

        // Hardcoded credentials (vulnerability demonstration)
        String creds = svc.getHardcodedCredentials();
        System.out.println("Hardcoded creds (bad practice): " + creds);

        // Unsafe SQL building (vulnerable pattern shown as string only)
        String badQuery = svc.buildInsecureSql("' OR '1'='1");
        System.out.println("Example vulnerable SQL (do not run): " + badQuery);

        // Weak hashing example (shows why MD5 is insecure)
        String weakHash = svc.weakHash("password123");
        System.out.println("Weak hash (MD5, don't use): " + weakHash);

        // Unsafe file path usage (demonstration only)
        String path = svc.getUnsafeFilePath("../etc/passwd");
        System.out.println("Constructed unsafe path (do not access): " + path);

        // Demonstrate safe versions
        System.out.println("Safe SQL (parameterized) example: " + svc.buildSafeSql("alice"));
        System.out.println("Safe hash (PBKDF2) example: " + svc.strongHash("password123"));
    }
}
