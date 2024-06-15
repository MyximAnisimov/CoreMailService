package mailcoremicroservice.roles;

public enum Roles {
    USER("USER"),
    MODERATOR("MODERATOR");

    private final String role;

    Roles(String role){
        this.role=role;
    }

}
