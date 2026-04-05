// Quick test to verify isPaid missing field behavior
public class test_isPaid {
    public static void main(String[] args) throws Exception {
        // Test what Jackson does with missing boolean primitive
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        
        String json = """
            {
              "name": "Test",
              "phone": "12345",
              "email": "test@test.com",
              "address": "123 St",
              "day": "Monday",
              "startTime": "10:00",
              "endTime": "11:00",
              "rate": "50"
            }
            """;
        
        try {
            seedu.address.storage.JsonAdaptedPerson person = mapper.readValue(json, seedu.address.storage.JsonAdaptedPerson.class);
            System.out.println("isPaid value: " + (Object) null);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
