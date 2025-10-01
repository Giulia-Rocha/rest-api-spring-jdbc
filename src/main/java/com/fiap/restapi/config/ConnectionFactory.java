package com.fiap.restapi.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Properties PROPS = loadProperties();

    private ConnectionFactory() {}


    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Arquivo application.properties não encontrado, usando apenas variáveis de ambiente.");
                return props; // Retorna props vazias se o arquivo não existir
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo application.properties", e);
        }
        return props;
    }
    public static Connection getConnection() throws SQLException {
        // 1. Tenta pegar da variável de ambiente.
        // 2. Se não existir, pega do arquivo .properties.
        String url = System.getenv().getOrDefault("DB_URL", PROPS.getProperty("db.url"));
        String user = System.getenv().getOrDefault("DB_USER", PROPS.getProperty("db.user"));
        String pass = System.getenv().getOrDefault("DB_PASSWORD", PROPS.getProperty("db.password"));

        if (url == null || user == null || pass == null) {
            throw new SQLException("Credenciais do banco de dados não foram configuradas (nem via environment variables, nem via application.properties)");
        }

        return DriverManager.getConnection(url, user, pass);
    }
}
