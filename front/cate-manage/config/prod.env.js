'use strict'
module.exports = {
  NODE_ENV: '"production"',
  CATE_API_BASE: JSON.stringify(process.env.CATE_API_BASE || 'http://localhost:8888')
}
