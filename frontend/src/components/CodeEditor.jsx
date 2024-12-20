import React from "react";

import styles from "../styles/CodeEditor.module.css"

const CodeEditor = ({code, setCode}) => {
    return (

        <textarea value={code} onChange={(e) => setCode(e.target.value)}
                  placeholder={"Write your program here..."}
                  className={styles.textarea}
        />
    )
}

export default CodeEditor