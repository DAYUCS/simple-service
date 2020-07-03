# Build
```
mvn install -DskipTests
```

# Containerize
```
mkdir -p target/dependency
cd target/dependency
jar -xf ../*.jar
cd ..
cd ..
docker build -t biandayu/simple-service .
```

# Deploy
```
kubectl apply -f deployment.yml
kubectl apply -f ss-ingress.yml
```

# Open Dashboard Kiali
```
istioctl dashboard kiali
```

# Open Dashboard Jaeger
```
istioctl dashboard jaeger
```

# Access the service
```
curl http://localhost/api/json/utc/now
```

# Dashboards:
[Kiali](OpenTracing-Kiali.png)

[Jaeger](OpenTracing-Jaeger.png)