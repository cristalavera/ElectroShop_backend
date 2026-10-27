FROM eclipse-temurin:21-jdk

WORKDIR /app

# Actualizar los certificados del sistema operativo
RUN apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates \
    && update-ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Mitigacion provisional del incidente eMudhra en el almacen de Java
RUN set -eu; for alias in \
    "emsignrootcag1 [jdk]" \
    "emsignrootcag2 [jdk]" \
    "emsigneccrootcag3 [jdk]" \
    "comodoaaaca [jdk]"; do \
    keytool -delete -cacerts -alias "$alias" -storepass changeit; \
    done

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8000

CMD ["java", "-jar", "target/electro-shop-0.0.1-SNAPSHOT.jar"]
