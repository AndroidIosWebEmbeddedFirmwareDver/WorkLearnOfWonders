/**
 * Created by wangxiaolong on 2017/7/13.
 */
var mysql      = require('mysql');
var  connection=mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    password : 'admin',
    database : 'repair'
});


// connection.connect();
// connection.query('SELECT 1 + 1 AS solution', function (error, results, fields) {
//     if (error) throw error;
//     console.log('The solution is: ', results[0].solution);
// });
//

//----------->增删改查<-----------


// //查
// connection.connect();
// var  sql = 'SELECT * FROM user_base_info';
//
// connection.query(sql,function (err, result) {
//     if(err){
//         console.log('[SELECT ERROR] - ',err.message);
//         return;
//     }
//
//     console.log('--------------------------SELECT----------------------------');
//     console.log(result);
//     console.log('------------------------------------------------------------\n\n');
// });
//
// connection.end();

// //增
// connection.connect();
// var  addSql = 'INSERT INTO user_base_info(id,name,status,create_time) VALUES(4,?,?,?)';
// var  addSqlParams = ['aaa', '0','2017-07-13', 'UTF-8'];
//
// connection.query(addSql,addSqlParams,function (err, result) {
//     if(err){
//         console.log('[INSERT ERROR] - ',err.message);
//         return;
//     }
//
//     console.log('--------------------------INSERT----------------------------');
//     //console.log('INSERT ID:',result.insertId);
//     console.log('INSERT ID:',result);
//     console.log('-----------------------------------------------------------------\n\n');
// });
//
// connection.end();

// //改
// connection.connect();
// var modSql = 'UPDATE user_base_info SET name = ? WHERE Id = ?';
// var modSqlParams = ['bbb',1];
// connection.query(modSql,modSqlParams,function (err, result) {
//     if(err){
//         console.log('[UPDATE ERROR] - ',err.message);
//         return;
//     }
//     console.log('--------------------------UPDATE----------------------------');
//     console.log('UPDATE affectedRows',result.affectedRows);
//     console.log('-----------------------------------------------------------------\n\n');
// });
//
// connection.end();

// //删
// connection.connect();
// var delSql = 'DELETE FROM user_base_info where id=4';
// connection.query(delSql,function (err, result) {
//     if(err){
//         console.log('[DELETE ERROR] - ',err.message);
//         return;
//     }
//
//     console.log('--------------------------DELETE----------------------------');
//     console.log('DELETE affectedRows',result.affectedRows);
//     console.log('-----------------------------------------------------------------\n\n');
// });
//
// connection.end();


// var a=7;
// var b=9;
// a=a+b;
// b=a-b;
// a = a -b;
// console.log(a);
// console.log(b);