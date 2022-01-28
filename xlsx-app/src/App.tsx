import React, { useState } from "react";
import logo from "./logo.svg";
import "./App.css";
import {
  Backdrop,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from "@material-ui/core";
import PreviewFile from "./example.png";
import SourceFile from "./source.png";
import axios from "axios";
export const rootURL = "http://localhost:8000"; // 수정해야됨
export const headers = {
  "Content-Type": "application/json",
};

export const SpringAxios = axios.create({
  baseURL: `${rootURL}`,
});
SpringAxios.interceptors.request.use(function (config) {
  return { ...config, headers: headers };
});

function App() {
  const [templateFile, setTemplateFile] = useState<File>();
  const [rawDataFile, setRawDataFile] = useState<File>();
  const [dialogOpen, setDialogOpen] = useState(false);
  const [sourceDialogOpen, setSourceDialogOpen]=useState(false);
  const [loading, setLoading] = useState(false);
  const [bank, setBank] = useState("");
  const [accountNumber, setAccountNumber]=useState("");
  const [accountTo, setAccountTo]=useState("");
  function downloadZipFile(template: File, rawData: File) {
    const formData = new FormData();
    formData.append("template", template);
    formData.append("source", rawData);
    formData.append("bank",bank);
    formData.append("account",accountNumber);
    formData.append("accountTo",accountTo);
    setLoading(true);
    SpringAxios.post(`/upload`, formData)
      .then((res: any) => {
        window.location.assign(`${rootURL}/download`);
          setLoading(false);
      })
//       z
      .catch(() => {
        setLoading(false);
        alert("Fail : Merge File");
      });
  }
  return (
    <>
      <div className="App" style={{marginRight:50, marginLeft:50, marginBottom: 150, marginTop:50}}>
        <div style={{ display: "flex", flexDirection: "column" }}>
          <div style={{ marginTop: 15 }}>
            본 프로그램은 사전 템플릿에 최적화 되어있습니다. 파일 업로드 시
            유의해주세요
            <div>지원 파일 포맷 : xlsx(OOXML)</div>
            <div style={{ marginTop: 10 }}>
              <Button
                onClick={() => {
                  setDialogOpen(true);
                }}
              >
                템플릿 예시 보기
              </Button>
            </div>
            <div style={{ marginTop: 10 }}>
              <Button
                onClick={() => {
                  setSourceDialogOpen(true);
                }}
              >
                소스 파일 예시 보기
              </Button>
            </div>
          </div>
          <div
            style={{
              minHeight: 200,
              maxHeight: 500,
              display: "flex",
              justifyContent: "center",
              marginTop: 50,
            }}
          >
            <div style={{ marginRight: "10vw" }}>
              Template File Upload{" "}
              {templateFile != undefined && (
                <img src="https://img.icons8.com/emoji/20/000000/check-mark-emoji.png" />
              )}
              <div
                style={{
                  height: "100%",
                  backgroundColor: "lightgreen",
                  marginTop: 10,
                }}
              >
                <div
                  style={{
                    height: "100%",
                    background: `url("https://img.icons8.com/material-outlined/384/000000/upload--v2.png")`,
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "cover",
                  }}
                >
                  <input
                    type="file"
                    style={{ opacity: 0, height: "100%" }}
                    onChange={(e: any) => setTemplateFile(e.target.files[0])}
                    accept=".xlsx"
                  />
                </div>
              </div>
              {templateFile != undefined && (
                <div style={{ display: "flex" }}>
                  <div>
                    <img src="https://img.icons8.com/dotty/40/000000/xlsx.png" />
                  </div>
                  <div style={{ marginTop: "auto", marginBottom: "auto" }}>
                    {templateFile.name}
                  </div>
                </div>
              )}
            </div>
            <div>
              Raw Data Files Upload
              {rawDataFile != undefined && (
                <img src="https://img.icons8.com/emoji/20/000000/check-mark-emoji.png" />
              )}
              <div
                style={{
                  height: "100%",
                  backgroundColor: "lightblue",
                  marginTop: 10,
                }}
              >
                <div
                  style={{
                    height: "100%",
                    background: `url("https://img.icons8.com/material-outlined/384/000000/upload--v2.png")`,
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "cover",
                  }}
                >
                  <input
                    type="file"
                    style={{ opacity: 0, height: "100%" }}
                    onChange={(e: any) => {
                      setRawDataFile(e.target.files[0]);
                    }}
                    accept=".xlsx"
                  />
                </div>
              </div>
              <div style={{ maxHeight: 200, overflow: "auto", marginTop: 10 }}>
                {rawDataFile != undefined && (
                  <>
                    <div style={{ display: "flex" }}>
                      <div>
                        <img src="https://img.icons8.com/dotty/40/000000/xlsx.png" />
                      </div>
                      <div style={{ marginTop: "auto", marginBottom: "auto" }}>
                        {rawDataFile.name}
                      </div>
                    </div>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
        <Dialog
          open={dialogOpen}
          onClose={() => {
            setDialogOpen(false);
          }}
          maxWidth={"xl"}
        >
          <DialogTitle>Template File Example</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description"></DialogContentText>
            <img src={PreviewFile} />
          </DialogContent>
          <DialogActions>
            <Button
              onClick={() => {
                setDialogOpen(false);
              }}
            >
              확인
            </Button>
          </DialogActions>
        </Dialog>
        <Dialog
          open={sourceDialogOpen}
          onClose={() => {
            setSourceDialogOpen(false);
          }}
          maxWidth={"xl"}
        >
          <DialogTitle>Raw Data File Example</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description"></DialogContentText>
            <img src={SourceFile} />
          </DialogContent>
          <DialogActions>
            <Button
              onClick={() => {
                setSourceDialogOpen(false);
              }}
            >
              확인
            </Button>
          </DialogActions>
        </Dialog>
        <div>
        <div style={{ paddingTop: 200 }}>
           <div><TextField label="은행명 입력" value = {bank} onChange={(e)=>{setBank(e.target.value)}}/></div>
           <div style={{marginTop:5}}><TextField label = "계좌번호 From" value={accountNumber} onChange={(e)=>{setAccountNumber(e.target.value)}}/></div>
           <div style={{marginTop:5}}><TextField label = "계좌번호 To" value={accountTo} onChange={(e)=>{setAccountTo(e.target.value)}}/></div>
          </div>
        </div>
        <div style={{ paddingTop: 80, marginBottom:80 }}>
          <Button
            color="primary"
            disabled={rawDataFile == undefined || templateFile == undefined}
            variant="outlined"
            onClick={() => {
              downloadZipFile(templateFile!, rawDataFile!);
            }}
          >
            <strong> Zip File Download</strong>
          </Button>
        </div>
        <Backdrop open={loading}>
          <CircularProgress color="inherit" />
        </Backdrop>
      </div>
    </>
  );
}

export default App;
