FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17
ENV ENV 'test'
ADD living-console.jar /
EXPOSE 8002
ENTRYPOINT exec java \
    --add-opens java.base/sun.net=ALL-UNNAMED \
    -server \
    -Dproject.name=live-console \
    -Duser.timezone=Asia/Shanghai \
    -jar -Dspring.profiles.active=${ENV} living-console.jar
