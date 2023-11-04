FROM openjdk:17

WORKDIR /app

COPY target/product-petru.jar /app/product-petru.jar

COPY products.json /app/products.json

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/product-petru.jar"]