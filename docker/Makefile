all: docker-all

docker-all: ead-payment build docker-up

ead-payment:
	@echo "Building" \

build:
	cd payment; \
	./mvnw clean install -DskipTests \

docker-up:
	docker-compose up --build payment-decoder











