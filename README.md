# Trabajo práctico de AED 2C2024, FCEN, UBA.
La consigna se puede ver en este [enlace](https://campus.exactas.uba.ar/pluginfile.php/581362/mod_resource/content/9/enunciado.pdf)
## Paso a paso para:
### Forkear y hacer pull request por primera vez:
1. Arriba a la derecha está el fork en el repo de GitHub a clonar.
2. Hacer el fork tal cual sin modificar nada (es irrelevante).
3. Clonar ESE repositorio nuevo (el tuyo):
`git clone <URL-del-repositorio>`
4. Atributo para linkear repos en caso de que el original tenga cambios:
`git remote add upstream https://github.com/roussineau/BestEffort`
5. Hacer los cambios que tengamos que hacer, o sea, programar.
6. Pasar todo al staging area, commitear y mandar al repositorio remoto:
`git add .`
`git commit -m "Descripción de los cambios"`
`git push origin main`
7. Ir al [repositorio original](https://github.com/Roussineau/BestEffort) del que hicimos el fork y abrir una pull request.
8. Mandar la pull y listo.
### Sincronizar los repos en caso de que el nuestro esté desactualizado
1. `git fetch upstream`
2. `git merge upstream/main`
3. `git push origin main`
4. Programar.
