'use strict'

// Old Taobao mirror URLs no longer work reliably. Keep locked versions and
// integrity checks, and fetch the same package archives from npm's registry.
// This script runs on the Docker build's copy, leaving the source lock intact.
const fs = require('fs')
const lock = JSON.parse(fs.readFileSync('package-lock.json', 'utf8'))

function updateRegistry (dependencies) {
  Object.keys(dependencies || {}).forEach(name => {
    const dependency = dependencies[name]
    if (dependency.resolved && /^https?:\/\/(?:registry\.npm\.taobao\.org|registry\.npmmirror\.com)\//.test(dependency.resolved)) {
      const filename = name.split('/').pop() + '-' + dependency.version + '.tgz'
      dependency.resolved = 'https://registry.npmjs.org/' + name + '/-/' + filename
    }
    updateRegistry(dependency.dependencies)
  })
}

updateRegistry(lock.dependencies)
fs.writeFileSync('package-lock.json', JSON.stringify(lock, null, 2) + '\n')
