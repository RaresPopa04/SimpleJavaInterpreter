import React from "react";
import styles from "../styles/CompilerResult.module.css"

const CompilerResult = ({result, error}) => {
    return (
        <div className={styles.container}>
            <h3 className={styles.outputTitle}>Output: </h3>
            {error ? (
                <pre className={styles.error}>{error}</pre>
            ):(
                <pre className={styles.pre}>{result}</pre>
            )}
        </div>
    )
}

export default CompilerResult