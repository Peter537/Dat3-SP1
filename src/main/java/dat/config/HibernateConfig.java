package dat.config;

import dat.model.*;
import dat.services.HobbyCategoryConverter;
import dat.services.HobbyTypeConverter;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HibernateConfig {

    private static EntityManagerFactory entityManagerFactory;
    private static String databaseName = "postgres";

    private static EntityManagerFactory buildEntityFactoryConfig() {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + databaseName + "?currentSchema=public");
            props.put("hibernate.connection.username", "postgres");
            props.put("hibernate.connection.password", "postgres");
            props.put("hibernate.show_sql", "true"); // show sql in console
            props.put("hibernate.format_sql", "true"); // format sql in console
            props.put("hibernate.use_sql_comments", "true"); // show sql comments in console

            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); // dialect for postgresql
            props.put("hibernate.connection.driver_class", "org.postgresql.Driver"); // driver class for postgresql
            props.put("hibernate.archive.autodetection", "class"); // hibernate scans for annotated classes
            props.put("hibernate.current_session_context_class", "thread"); // hibernate current session context
            props.put("hibernate.hbm2ddl.auto", "create"); // hibernate creates tables based on entities
            return getEntityManagerFactory(configuration, props);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static EntityManagerFactory getEntityManagerFactory(Configuration configuration, Properties props) {
        configuration.setProperties(props);
        // TODO: addAnnotatedClasses(configuration, X.class, Y.class, Z.class);
        addAnnotatedClasses(configuration,
                Address.class,
                Event.class,
                Hobby.class,
                Person.class,
                PersonHobby.class,
                Zip.class);

        configuration.addAttributeConverter(HobbyTypeConverter.class);
        configuration.addAttributeConverter(HobbyCategoryConverter.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        System.out.println("Hibernate Java Config serviceRegistry created");
        SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
        return sf.unwrap(EntityManagerFactory.class);
    }

    private static void addAnnotatedClasses(Configuration configuration, Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            configuration.addAnnotatedClass(annotatedClass);
        }
    }

    public static EntityManagerFactory getEntityManagerFactoryConfig(String databaseName) {
        if (entityManagerFactory == null || !HibernateConfig.databaseName.equals(databaseName)) {
            HibernateConfig.databaseName = databaseName;
            entityManagerFactory = buildEntityFactoryConfig();
        }

        return entityManagerFactory;
    }
}