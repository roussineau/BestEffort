# Trabajo práctico de AED 2C2024, FCEN, UBA.
La consigna se puede ver [aquí](https://campus.exactas.uba.ar/pluginfile.php/581362/mod_resource/content/9/enunciado.pdf)

## Paso a paso para forkear y hacer pull request por primera vez:
1. Arriba a la derecha está el fork en el repo de GitHub a clonar.
2. Hacer el fork tal cual sin modificar nada (es irrelevante).
3. Clonar ESE repositorio nuevo (el tuyo):
`git clone <URL-del-repositorio>`
4. Hacer los cambios que tengamos que hacer, o sea, programar.
5. Pasar todo al staging area, commitear y mandar al repositorio remoto:
`git add .`
`git commit -m "Descripción de los cambios"`
`git push origin nombre-de-la-rama`
6. Ir al [repositorio original](https://github.com/Roussineau/BestEffort) del que hicimos el fork y abrir una pull request.
7. Mandar la pull y listo.

## Linkear los repos en caso de que el nuestro esté desactualizado
1. `git remote add upstream https://github.com/roussineau/BestEffort`
2. `git checkout main`
3. `git fetch upstream`
4. `git merge upstream/main`
5. `git push origin main`
