#Creacion de contenedor

compilar la aplicacion 

```
gradlew quarkusBuild
```
```
cd authors
docker build -t app-authors:1.0.0 .
```
```
cd books
docker build -t app-books:1.0.0 .
```
```
podman run –d -p 8080:8080 –name books1 localhost/app-books:1.0.0 
```