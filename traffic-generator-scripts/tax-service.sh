#!/bin/bash
#This script will handle testing the TAX-SERVICE endpoints

echo "---> Testing the TAX-SERVICE endpoints..."
echo "---> Call format: tax-service-test.sh http://tax-service-endpoint-url 1000"
SERVICE_URL=${1}
HIT_COUNT=${2}

TAX_CLASS_ENDPOINTS=("${SERVICE_URL}/rest/tax-service/tax-class/list-partial/1" "${SERVICE_URL}/rest/tax-service/tax-class/list-partial/-1" "${SERVICE_URL}/rest/tax-service/tax-class/storeId/1/tax-code/A" "${SERVICE_URL}/rest/tax-service/tax-class/storeId/-1/tax-code/A")
TAX_RATES_ENDPOINTS=("${SERVICE_URL}/rest/tax-service/tax-rates/list-by-store/1" "${SERVICE_URL}/rest/tax-service/tax-rates/list-by-store/-1")
TEST_ENDPOINTS=($(echo ${TAX_CLASS_ENDPOINTS[*]}) + $(echo ${TAX_RATES_ENDPOINTS[*]}))

#LOOP TRHOUGH THE ENDPOINTS AND MAKE HTTP REQUESTS
echo "---> Making http requests..."

for endpoint in ${TEST_ENDPOINTS[*]}
do
    echo "---> Testing service endpoint : ${endpoint}"
    for index in $(seq 1 ${HIT_COUNT})
    do
        echo "---> HIT COUNT --->: ${index}"
        echo "---> Hitting endpoint: ${endpoint}..."
        curl ${endpoint}
    done
    if [ $index -eq ${HIT_COUNT} ];then
        echo "---> ${endpoint} hit ${HIT_COUNT} times...Moving to the next endpoint..."
    fi
done
echo "---> Test of TAX-SERVICE completed...Exiting..."

