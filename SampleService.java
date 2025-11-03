package com.example.vulndemo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * SampleService shows insecure patterns (as strings or simulated outputs)
 * and provides improved alternatives. None of the insecure methods perform
 * dangerous actions (e.g., no DB calls, no command execution).
 */
public class SampleService {

    // ----- Vulnerability 1: Hardcoded credentials -----
    // Why it's bad: credentials in code may leak via source control, logs, or binaries.
    // Mitigation: use a secure secrets store / environment variables and rotate secrets.
    public String getHardcodedCredentials() {
        // INTENTIONAL insecure example (do NOT use in production)
        String username = "admin";
        String password = "P@ssw0rd!"; // hardcoded secret -- vulnerability
        return username + ":" + password;
    }

    // ----- Vulnerability 2: SQL injection pattern (string concatenation) -----
    // This method returns a SQL string showing a vulnerable pattern.
    // DO NOT execute this string; instead use PreparedStatement with parameters.
    public String buildInsecureSql(String userInput) {
        // Vulnerable string concatenation
        return "SELECT * FROM users WHERE username = '" + userInput + "';";
    }

    // Safer alternative – show parameterized SQL snippet (as string)
    public String buildSafeSql(String usernameParamName) {
        // Use parameter placeholder; in real code, pass the parameter to PreparedStatement.
        return "SELECT * FROM users WHERE username = ? /* bind parameter: " + usernameParamName + " */;";
    }

    // ----- Vulnerability 3: Weak hashing (MD5) -----
    // Demonstrates MD5 hashing result (MD5 is considered broken for password hashing).
    // Mitigation: use PBKDF2 / bcrypt / Argon2 with salt and iterations.
    public String weakHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // weak algorithm
            byte[] digest = md.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            return "md5-not-available";
        }
    }

    // Safer hashing: demonstrate expected output (not full implementation here)
    // In real code, use a well-tested library to create salted PBKDF2/bcrypt/Argon2 hashes.
    public String strongHash(String input) {
        // For demonstration, return a placeholder string describing the right approach.
        return "PBKDF2(salt, iterations, derivedKey)"; // do real hashing with secure libs
    }

    // ----- Vulnerability 4: Path traversal (unsafe file paths) -----
    // This method constructs a file path from user input (vulnerable pattern).
    // DO NOT open arbitrary paths. Mitigate by validating and normalizing paths,
    // and by using an allowlist of files.
    public String getUnsafeFilePath(String userSuppliedPath) {
        // Vulnerable concatenation (simply demonstration)
        String base = "/var/www/data/";
        return base + userSuppliedPath;
    }

    // ----- Vulnerability 5: Unsafe deserialization (exposed as note) -----
    // We DO NOT implement insecure deserialization here. Instead we explain:
    // - Unsafe deserialization happens when untrusted data is deserialized into objects.
    // - Mitigation: avoid Java native serialization for untrusted data; use safe formats (JSON)
    //   and validate types; use allowlists or libraries that prevent arbitrary type instantiation.

    // Note: Any real networking, DB, or shell execution code is intentionally omitted.
}
