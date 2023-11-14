package main

import (
	"bytes"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"strconv"
	"time"
)
//用Go语言定义一个结构体
type basicInfo struct{
	traid   string `json:"traid"`
	pname string `json:"pname"`
	paddress   string `json:"paddress"`
	ptime     string `json :"ptime"`
	pperson   string `json:"pperson"`
	tperson   string `json:"tperson"`
	tbeginaddress   string `json:"tbeginaddress"`
	tarriveaddress   string `json:"tarriveaddress"`
	tname   string `json:"tname"`
	sperson   string `json:"sperson"`
	stime   string `json:"stime"`
	saddress   string `json:"saddress"`
	sname   string `json:"sname"`
	role   string `json:"role"`
}
//智能合约执行三个步骤，安装-实例化-升级
//初始化方法，在合约实例化的时候自动执行
func (t *basicInfo) Init (stub shim.ChaincodeStubInterface) pb.Response{
	return shim.Success(nil)
}
//invoke和delete都进行了修改，都要上块（新增、修改和删除）
//除了初始化，所有方法都要走这
func (t *basicInfo) Invoke(stub shim.ChaincodeStubInterface) pb.Response{
	funcName,args := stub.GetFunctionAndParameters()
	if(funcName=="save"){
		return t.saveBasic(stub,args)
	}else if(funcName=="query"){
		return t.queryBasic(stub,args)
	}else if(funcName=="delete"){
		return t.deleteBasic(stub,args)
	}else if(funcName=="queryAll"){
		return t.queryAll(stub,args)
	}else{
		return shim.Error("no such function")
	}
}
//添加数据
func (t *basicInfo) saveBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if(len(args)!=2){//判断参数个数
		return shim.Error("except two args")
	}else{
		err:=stub.PutState(args[0],[]byte(args[1]))//存入数据库并上块
		if(err!=nil) {
			return shim.Error(err.Error())
		}
		return shim.Success(nil)
	}

}
//查询数据
func (t *basicInfo) queryBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response{

	if(len(args)!=1){
		return shim.Error("except one arg")
	}else{
	  value,err :=stub.GetState(args[0])
	  if(err!=nil){
	  	shim.Error("no data found")
	  }
	  return shim.Success(value)
	}
}

func (t *basicInfo) queryAll(stub shim.ChaincodeStubInterface, args []string) pb.Response{
	if(len(args)!=1){
		return shim.Error("except one arg")
	}
	resultsIterator,err:= stub.GetHistoryForKey(args[0])
	if err!=nil{
		return shim.Error(err.Error())
	}

	defer resultsIterator.Close()

	var buffer bytes.Buffer
	buffer.WriteString("[")
	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		response, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"TID\":")
		buffer.WriteString("\"")
		buffer.WriteString(response.TxId)
		buffer.WriteString("\"")

		buffer.WriteString(", \"THistory\":")

		if response.IsDelete {
			buffer.WriteString("null")
		} else {
			buffer.WriteString(string(response.Value))
		}
		buffer.WriteString(", \"TTime\":")
		buffer.WriteString("\"")
		buffer.WriteString(time.Unix(response.Timestamp.Seconds, int64(response.Timestamp.Nanos)).String())
		buffer.WriteString("\"")

		buffer.WriteString(", \"IsDelete\":")
		buffer.WriteString("\"")
		buffer.WriteString(strconv.FormatBool(response.IsDelete))
		buffer.WriteString("\"")

		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	return shim.Success(buffer.Bytes())
}


//删除数据
func (t *basicInfo) deleteBasic(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	if(len(args)!=1){
		return shim.Error("except one args")
		}else{
			A := args[0] //把第0个变量赋值给变量A
			err := stub.DelState(A) //删除A的账户
			if(err!=nil){
				shim.Error("Failed to delete state")
			}
			return shim.Success(nil)
		}
	}




func main(){
	err:=shim.Start(new(basicInfo))
	if(err!=nil){
		fmt.Println("emr basicInfo chaincode start error")
	}
}
