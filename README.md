# Deploy as Kubernetes service

## Build
```
mvn install -DskipTests
```

## Containerize
```
mkdir -p target/dependency
cd target/dependency
jar -xf ../*.jar
cd ..
cd ..
docker build -t biandayu/simple-service:v1.2.0 .
docker push biandayu/simple-service:v1.2.0
```

## Deploy
```
kubectl apply -f deployment.yml
kubectl apply -f nginx-ingress.yml
kubectl get -n default deploy -o yaml | linkerd inject - | kubectl apply -f -
```

## Open Dashboard Jaeger
```
linkerd jaeger dashboard
```

## Access the service
Use 'minikube ip' to get ingress ip address 
```
curl http://[ingress ip address]/api/json/v1/1/random.php
```