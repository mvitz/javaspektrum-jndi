docker run \
  --rm \
  -p 389:389 \
  -p 636:636 \
  --env LDAP_ORGANISATION="mvitz.de" \
  --env LDAP_DOMAIN="mvitz.de" \
  --env LDAP_BASE_DN="dc=mvitz,dc=de" \
  --volume $(pwd)/ldif:/container/service/slapd/assets/config/bootstrap/ldif/custom:ro \
  osixia/openldap:1.5.0 \
  --copy-service
