FROM bellsoft/liberica-openjdk-alpine-musl:17
EXPOSE @docker.port@
ADD /target/@project.build.finalName@.@project.packaging@ @project.build.finalName@.@project.packaging@
CMD java -jar -Dfile.encoding=UTF8 -Duser.timezone=America/Mexico_City \
    -Dspring.profiles.active=@spring.profiles.active@ \
    @project.build.finalName@.@project.packaging@