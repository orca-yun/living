FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17
ENV ENV 'test'
ADD living-task.jar /
ENTRYPOINT exec java \
    --add-opens java.base/sun.net=ALL-UNNAMED \
    -server \
    -Dproject.name=live-task \
    -Duser.timezone=Asia/Shanghai \
    -jar -Dspring.profiles.active=${ENV} living-task.jar
