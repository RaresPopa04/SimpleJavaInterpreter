import CodeEditor from "./components/CodeEditor";
import {useState} from "react";
import {executeProgram} from "./api/InterpreterAPI";
import CompilerResult from "./components/CompilerResult";
import styles from "./styles/App.module.css"


function App() {
    const [code, setCode] = useState("")
    const [output, setOutput] = useState("No output yet")
    const [error, setError] = useState("")

    const handleCode = async () => {
        setError("")
        setOutput("")

        try {
            const result = await executeProgram(code);
            console.log(result)
            setOutput(result.result)
        } catch (err) {
            setError(err.response?.data?.result|| "Failed to execute the program")
        }
    }
    return (
        <div className={styles.container}>

            <h1 className={styles.title}>Simple Language Interpreter</h1>
            <CodeEditor code={code} setCode={setCode}/>
            <button onClick={handleCode} disabled={!code} className={styles.runBtn}>
                Run Code
            </button>
            <CompilerResult error={error} result={output}/>
        </div>
    );
}

export default App;