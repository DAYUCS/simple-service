# Build
mvn package

# Containerize
mkdir target/dependency
cd target/dependency
jar -xf ../*.jar
cd ..
cd ..
docker build -t biandayu/simple-service

# Deploy
kubectl apply -f deployment.yml
kubectl apply -f ss-ingress.yml

# Open Dashboard Kiali
istioctl dashboard kiali

# Open Dashboard Jaeger
istioctl dashboard jaeger