@Before("execution(* com.org.example..*(..))")
public void pointcutBeforeAllExampleMethods(JoinPoint jp) {
    System.out.println("Calling: " + jp.getSignature().getName());
}
